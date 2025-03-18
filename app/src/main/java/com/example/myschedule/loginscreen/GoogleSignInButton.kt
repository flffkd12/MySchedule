package com.example.myschedule.loginscreen

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myschedule.R
import com.example.myschedule.Routes
import com.example.myschedule.monthlyschedule.MonthlyScheduleViewModel
import com.example.myschedule.ui.theme.Black
import com.example.myschedule.ui.theme.White
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

@Composable
fun GoogleSignInButton(
    userViewModel: UserViewModel,
    monthlyScheduleViewModel: MonthlyScheduleViewModel,
    navController: NavController
) {

    val context = LocalContext.current
    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.login_google_client_id)).build()
    }

    val googleSignInClient = remember { GoogleSignIn.getClient(context, gso) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                val googleTask = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                val account = googleTask.result!!
                val auth = FirebaseAuth.getInstance()
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                auth.signInWithCredential(credential)
                    .addOnCompleteListener { authTask ->
                        if (authTask.isSuccessful) {
                            val user = auth.currentUser
                            if (user != null) {
                                userViewModel.saveUser(user.email.toString())
                                userViewModel.setUserEmail(user.email.toString())
                                userViewModel.setUserName(user.displayName.toString())
                                monthlyScheduleViewModel.fetchScheduleList(context)
                                navController.navigate(Routes.MAIN_SCREEN)
                            } else {
                                println("Successful firebase auth, but no user information.")
                            }
                        } else {
                            println("Firebase authentication failed for unexpected reason: ${authTask.exception}.")
                        }
                    }
            } catch (e: ApiException) {
                println("Google login failed: Check if using right web client id or right SHA-1 key.")
            } catch (e: NullPointerException) {
                println("Google login failed: Check if you used !! sign at null variable.")
            } catch (e: Exception) {
                println("Google login failed: Unexpected error - ${e.message}.")
            }
        } else {
            println("Google login failed: Abnormal result of activity result. Result code: ${result.resultCode}.")
            println("1. User might have canceled login process during login.")
            println("2. Check firebase console and google cloud console if the setting is right.")
        }
    }

    Button(
        onClick = { launcher.launch(googleSignInClient.signInIntent) },
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = White,
            contentColor = Black.copy(0.54f)
        ),
        border = BorderStroke(width = 1.dp, color = Color(0xFF747775)),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.height(48.dp).fillMaxWidth(0.6f),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(R.drawable.google_logo),
                contentDescription = null,
                modifier = Modifier.size(32.dp)
            )

            Spacer(Modifier.weight(1f))

            Text(text = stringResource(R.string.login_google_button_name), fontSize = 16.sp)

            Spacer(Modifier.weight(1f))
        }
    }
}

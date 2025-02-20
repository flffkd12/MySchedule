package com.example.myschedule.login

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myschedule.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException

@Composable
fun Login(loginViewModel: LoginViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Spacer(Modifier.weight(1f))
        Text(
            text = stringResource(R.string.app_name),
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = stringResource(R.string.app_description),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(Modifier.height(12.dp))
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = stringResource(R.string.app_logo),
            modifier = Modifier.fillMaxWidth(0.4f).aspectRatio(1.25f)
        )
        Spacer(Modifier.weight(2f))
        GoogleSignInButton(loginViewModel)
        Spacer(Modifier.weight(1f))
    }
}

@Composable
fun GoogleSignInButton(loginViewModel: LoginViewModel) {
    val context = LocalContext.current
    val gso = remember {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestServerAuthCode(context.getString(R.string.google_client_id)).build()
    }

    val googleSignInClient = remember { GoogleSignIn.getClient(context, gso) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                loginViewModel.setAuthCode(account.serverAuthCode)
            } catch (e: ApiException) {
                loginViewModel.setAuthCode(null)
                println("Login failed.")
                println("1. Check the 'Client ID' if it matches with Google Cloud Console.")
                println("2. Check the latest authorization dependency.")
                println("3. If none of it than check the error code: ${e.statusCode}")
            }
        } else {
            loginViewModel.setAuthCode(null)
            println("Login failed.")
            println("1.Maybe user canceled login process.")
            println("2.If not, Refactor code related to OAuth authorization.")
        }
    }

    Button(
        onClick = { launcher.launch(googleSignInClient.signInIntent) },
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White,
            contentColor = Color.Black.copy(0.54f)
        ),
        border = BorderStroke(width = 1.dp, color = Color(0xFF747775)),
        contentPadding = PaddingValues(8.dp),
        modifier = Modifier.height(48.dp).fillMaxWidth(0.6f),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(R.drawable.google_logo),
                contentDescription = stringResource(R.string.google_logo),
                modifier = Modifier.size(32.dp)
            )
            Spacer(Modifier.weight(1f))
            Text(text = stringResource(R.string.google_login_button_name), fontSize = 16.sp)
            Spacer(Modifier.weight(1f))
        }
    }
}
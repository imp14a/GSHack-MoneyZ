package mx.longbit.hackatonproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.KeyguardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class LoginActivity extends AppCompatActivity {

    private AppCompatEditText txtUser, txtPass;
    private Button btnLogin;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        checkBiometricSupport();


        ImageView im = findViewById(R.id.imageView);

        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticateUser(view);
            }
        });

    }



    void authenticateUser(View view) {

        BiometricPrompt.PromptInfo biometricPrompt;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {


            biometricPrompt = new BiometricPrompt.PromptInfo.Builder()
                    .setTitle("Biometric Demo")
                    .setSubtitle("Authentication is required to continue")
                    .setDescription("This app uses biometric authentication to protect your data.")
                    .setNegativeButtonText("cancel")
                    .build();
            Executor newExecutor = Executors.newSingleThreadExecutor();

            final BiometricPrompt myBiometricPrompt = new BiometricPrompt(this, newExecutor, new BiometricPrompt.AuthenticationCallback() {
                public void onAuthenticationError(int errorCode, CharSequence errString) {
                    super.onAuthenticationError(errorCode, errString);
                    if (errorCode == BiometricPrompt.ERROR_NEGATIVE_BUTTON) {
                    } else {
                        Log.w("NO", "PasaB");


//Print a message to Logcat//


                    }
                }

//onAuthenticationSucceeded is called when a fingerprint is matched successfully//

                @Override
                public void onAuthenticationSucceeded( BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);

                    Log.w("si", "Pasa");
                    Intent i = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(i);

//Print a message to Logcat//


                }

//onAuthenticationFailed is called when the fingerprint doesn’t match//

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();

//Print a message to Logcat//


                }
            });

            myBiometricPrompt.authenticate(biometricPrompt);
        }else{
            Log.w("NO", "Pasa");
        }




    }

    private Boolean checkBiometricSupport() {

        KeyguardManager keyguardManager =
                (KeyguardManager) getSystemService(KEYGUARD_SERVICE);

        PackageManager packageManager = this.getPackageManager();

        if (!keyguardManager.isKeyguardSecure()) {

            return false;
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.USE_BIOMETRIC) !=
                PackageManager.PERMISSION_GRANTED) {


            return false;
        }

        if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT))
        {
            return true;
        }

        return true;
    }


    private BiometricPrompt.AuthenticationCallback getAuthenticationCallback() {

        return new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode,
                                              CharSequence errString) {

                super.onAuthenticationError(errorCode, errString);
            }



            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }

            @Override
            public void onAuthenticationSucceeded(
                    BiometricPrompt.AuthenticationResult result) {

                super.onAuthenticationSucceeded(result);
            }
        };
    }
}

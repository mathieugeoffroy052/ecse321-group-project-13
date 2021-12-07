package ca.mcgill.ecse321.libraryservice_android;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import ca.mcgill.ecse321.libraryservice_android.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

import android.webkit.*;


public class MainActivity extends AppCompatActivity {


    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private WebView mainWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        Reference: https://stackoverflow.com/questions/7305089/how-to-load-external-webpage-in-webview?fbclid=IwAR0bhG4ziLzhUPmwcKjVeDijMJeO00vgNSddF6KDtwc0YfQvJddd-BQtaRE
        Our group used the following code used the Reference to learn how to link the frontend to the Android app.
        In other words, this was used so that the android app opens the website app when it is opened
         */
        super.onCreate(savedInstanceState);


        mainWebview  = new WebView(this);

        mainWebview.getSettings().setJavaScriptEnabled(true);


        mainWebview .loadUrl("https://libraryservice-frontend-g13.herokuapp.com/#/");
        setContentView(mainWebview );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
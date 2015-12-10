package org.ipfx.example;

import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ipfx.Interpolator;

public class MainActivity extends ActionBarActivity {

  Button button;
  Button testButton;
  EditText urlEdit;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    button = (Button)findViewById(R.id.button);
    testButton = (Button)findViewById(R.id.test_button);
    urlEdit = (EditText) findViewById(R.id.url_edit);

    // Host is not needed in the url. All data is encoded in the search part
    //
    urlEdit.setText("?p=807e7fff7de97fc1&l=082b1afc67d23af8f0829baf767d00b00c");
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        runAnimation();
      }
    });

  }



  private void runAnimation(){

    View testLayout = findViewById(R.id.test_layout);
    int height = testLayout.getHeight() - testButton.getHeight();
    int startY = (int)(height * 0.75f);
    int endY = (int)(height * 0.25);

    String url = urlEdit.getText().toString();
    // Create interpolator using data encoded in the url
    final Interpolator interpolator = Interpolator.parseUrl(url);

    // interpolator can be null if data is not correct
    if(interpolator != null) {

      ObjectAnimator animator = ObjectAnimator.ofFloat(testButton, View.Y, startY, endY);
      animator.setDuration(1000);

      // set custom android interpolator
      animator.setInterpolator(new TimeInterpolator() {
        @Override
        public float getInterpolation(float input) {
          // return value calculated in org.ipfx.Interpolator
          return interpolator.calc(input);
        }
      });
      animator.start();

    } else {
      Toast.makeText(getApplicationContext(), "Invalid url data", Toast.LENGTH_SHORT).show();
    }

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

    String url = "";

    switch (id){
      case R.id.ex1: url = "?p=807e7fff7de97fc1&l=082b1afc67d23af8f0829baf767d00b00c"; break;
      case R.id.ex2: url = "?p=fffe7fff6ad47ec3c7ff7e03&l=39172a9a138f86aa893892793d7"; break;
      case R.id.ex3: url = "?p=7ffffffe4d90fffe98d3fffe&l=58c56bb66588db796f58e16853f"; break;
      case R.id.ex4: url = "?p=fffe7fff711380fb&l=39004a92f597fe7849"; break;
      case R.id.ex5: url = "?p=7ffffffe&l=3cff0829f"; break;
      case R.id.ex6: url = "?p=7fff7fff7e537fff&l=5942daa555962555a9"; break;
      case R.id.ex7: url = "?p=7fff7fff3e31fffebefd0000&l=444"; break;
      case R.id.ex8: url = "?p=fffefffe243977233eb917a1601d50bc8072fffe938780fbcdd4d89c&l=6666666"; break;
    }

    urlEdit.setText(url);

    return super.onOptionsItemSelected(item);
  }
}

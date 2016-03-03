//package mediaclub.app.wanderlust;
//
//import android.graphics.Color;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//
//import com.github.paolorotolo.appintro.AppIntro;
//import com.github.paolorotolo.appintro.AppIntro2;
//import com.github.paolorotolo.appintro.AppIntroFragment;
//
//public class NewIntro extends AppIntro2 {
//
//    @Override
//    public void init(Bundle savedInstanceState) {
//
//        // Add your slide's fragments here.
//        // AppIntro will automatically generate the dots indicator and buttons.
//
//        Intro1 first_fragment = new Intro1();
//        Intro2 second_fragment = new Intro2();
//        Intro3 third_fragment = new Intro3();
//        Intro4 fourth_fragment = new Intro4();
//        Intro5 fifth_fragment = new Intro5();
//        Intro6 sixth_fragment = new Intro6();
//
//        addSlide(first_fragment);
//        addSlide(second_fragment);
//        addSlide(third_fragment);
//        addSlide(fourth_fragment);
//        addSlide(fifth_fragment);
//        addSlide(sixth_fragment);
//
//        // Instead of fragments, you can also use our default slide
//        // Just set a title, description, background and image. AppIntro will do the rest.
//        //addSlide(AppIntroFragment.newInstance(title, description, image, background_colour));
//
//        // OPTIONAL METHODS
//        // Override bar/separator color.
//
//
//        // Turn vibration on and set intensity.
//        // NOTE: you will probably need to ask VIBRATE permisssion in Manifest.
//        setVibrate(true);
//        setVibrateIntensity(30);
//
//        setFadeAnimation();
//
//        //setIndicatorColor(R.color.colorPrimaryDark,R.color.colorPrimaryDark);
//    }
//
//    @Override
//    public void onDonePressed() {
//
//    }
//
//
//}

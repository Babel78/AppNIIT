package com.example.axel.GIOT;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.ViewSwitcher;

import com.example.axel.GIOT.models.mensajes;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONException;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private String text_rb="";
    private RadioGroup rg;
    private ImageSwitcher imageSwitcher;
    private static final Integer DURATION = 2500;
    private Timer timer = null;
    private int [] images= {R.drawable.fotogrupo,R.drawable.f2,R.drawable.f3,R.drawable.f4,R.drawable.f5};
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference mydat=database.getReference("Mensajes");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageSwitcher= (ImageSwitcher) findViewById(R.id.is);
        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            public View makeView() {
                return new ImageView(MainActivity.this);
            }
        });
        rg=(RadioGroup) findViewById(R.id.rgroup);
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
        imageSwitcher.setInAnimation(fadeIn);
        imageSwitcher.setOutAnimation(fadeOut);
        startSlider();
    }

    public void onClick(View view) throws JSONException {
        String title="",message="";
        if(!text_rb.equals("")){
            Date d=new Date();
            mensajes m=new mensajes(text_rb,d.toString());
            mydat.push().setValue(m);
            title="MENSAJE ENVIADO";
            message="Gracias por su tiempo!";
            rg.clearCheck();
            MostrarAlertar(title,message);
            text_rb="";
        }
        else{
            title="MENSAJE NO ENVIADO";
            message="Por favor califique nuestra participación :)!";
            MostrarAlertar(title,message);
        }
    }

    private void MostrarAlertar(String title , String message){
        AlertDialog.Builder alerta= new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        AlertDialog alert=alerta.create();
        alert.show();
    }

    public void enviar_form(View view) {
        switch (view.getId()){
            case R.id.mal:
                text_rb="Mal";
                break;
            case R.id.regular:
                text_rb="Regular";
                break;

            case R.id.normal:
                text_rb="Normal";
                break;

            case R.id.bien:
                text_rb="bien";
                break;

            case R.id.awesome:
                text_rb="Sorprendente";
                break;
        }

    }

    public void abrirlink(View view) {
        String url="";
        switch (view.getId()){
            case R.id.link_universidad:
                url="http://www.unmsm.edu.pe/";
                break;
            case R.id.link_GIOT:
                url="https://jguerra91.wixsite.com/niitroboticaiot";
                break;
        }
        Uri uri=Uri.parse(url);
        Intent intentnew=new Intent(Intent.ACTION_VIEW,uri);
        startActivity(intentnew);
    }
    private  int position=0;
    public void startSlider() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {

            public void run() {
                // avoid exception:
                // "Only the original thread that created a view hierarchy can touch its views"
                runOnUiThread(new Runnable() {
                    public void run() {
                        imageSwitcher.setImageResource(images[position]);
                        position++;
                        if (position == images.length) {
                            position = 0;
                        }
                    }
                });
            }

        }, 0, DURATION);
    }
}

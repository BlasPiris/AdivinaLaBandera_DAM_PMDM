package com.example.adivinalabandera;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    //ARRAY MULTIDIMENSONAL DE PAISES
    Object [][] arrayBanderas = {
            {0,"España", R.drawable.es},
            {1,"Portugal", R.drawable.pt},
            {2,"Francia", R.drawable.fr},
            {3,"Italia", R.drawable.it},
            {4,"Inglaterra", R.drawable.eng},
            {5,"Alemania", R.drawable.de},
            {6,"Estados Unidos", R.drawable.us},
            {7,"Argentina",R.drawable.ar},
            {8,"Suiza", R.drawable.ch},
            {9,"Bélgica", R.drawable.be},
            {10,"Rusia", R.drawable.ru},
            {11,"China", R.drawable.cn},
            {12,"Japón", R.drawable.jp},
            {13,"Israel", R.drawable.il},
            {14,"Arabia Saudí", R.drawable.sa},
            {15,"Emiratos Arabes Unidos", R.drawable.ae},
            {16,"Bolivia", R.drawable.bo},
            {17,"Canadá", R.drawable.ca},
            {18,"Australia", R.drawable.au},
            {19,"Paises Bajos", R.drawable.nl},
            {20,"Andorra", R.drawable.ad},
    };

    //VARIABLES DEL JUEGO
    int nBanderas=2;
    int intentos=0;
    int aciertos=0;
    int rachaAciertos=0;
    int record=0;

    //VARIABLES TEXVIEW
    TextView tintentos;
    TextView taciertos;
    TextView trachaAciertos;
    TextView trecord;
    TextView nombreBandera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //RELACIONAMOS LAS VARIABLES CON SU TEXVIEW DE LA VISTA
        tintentos=(TextView) findViewById(R.id.intentos);
        taciertos=(TextView) findViewById(R.id.aciertos);
        trachaAciertos=(TextView) findViewById(R.id.racha_aciertos);
        trecord=(TextView) findViewById(R.id.record);
        nombreBandera=(TextView) findViewById(R.id.nombreBandera);

        //INICIALIZAMOS JUEGO CON EL NUMERO DE BANDERAS
        generarJuego(nBanderas);


    }

    //METODO QUE GENERA UN NUEVA COMBINACIÓN Y PAIS
    private void generarJuego(int nBanderas) {

        //SI EL NUMERO DE BANDERAS INDICADO ES MAYOR A LAS EXISTENTES EN ARRAY, USAMOS EL MAXIMO DISPONIBLE
        if(nBanderas>arrayBanderas.length){
            nBanderas=arrayBanderas.length;
        }

        //CREAMOS UN ARRAY PARA ALMACENAR LAS BANDERAS DEL JUEGO
        List<Object> banderasJuego = new ArrayList<Object>();
        //COGEMOS UN PAIS ALEATORIO
        int ran = new Random().nextInt(arrayBanderas.length);
        //ALMACENAMOS EL PAIS QUE DEBEMOS ACERTAR Y LO GUARDAMOS EN EL ARRAY
        Object[] banderaGanadora=arrayBanderas[ran];
        banderasJuego.add(banderaGanadora);

        //ALMACENAMOS OTROS DATOS DEL PAIS
        int idGanadora= (int) banderaGanadora[0];
        String nombreGanadora= (String) banderaGanadora[1];
        //ARRAY PARA GUARDAR LOS ID DE LOS PAISES DEL JUEGO
        List<Integer> arrayIdBanderas = new ArrayList<Integer>();
        arrayIdBanderas.add(idGanadora);

        //RESTAMOS 1 AL NUMERO DE BANDERAS
        nBanderas--;

        //GENERAMOS EL RESTO DE BANDERAS

       for (int i = 0; i < nBanderas; i++) {
           //GENERAMOS UNA BANDERA NUEVA SIEMPRE QUE NO ESTÉ YA EN EL ARRAY DE BANDERAS DEL JUEGO
           Object[] bandera;
            ran = new Random().nextInt(arrayBanderas.length);
            if(arrayIdBanderas.contains(ran)){
                i--;
            }else{
                bandera=arrayBanderas[ran];
                banderasJuego.add(bandera);
                arrayIdBanderas.add((Integer) bandera[0]);
            }
        }

       //REVOLVEMOS EL ARRAY
        Collections.shuffle(banderasJuego);

       //LLAMAMOS AL METODO PARA PINTAR LAS BANDERAS EN LA PANTALLA
       pintarBanderas(nBanderas,banderasJuego,idGanadora);
       //LLAMAMOS AL METODO DEL NOMBRE DEL PAIS A ADIVINAR
       pintarNombreBandera(nombreGanadora);

    }


    //METODO PARA PINTAR LAS BANDERAS EN PANTALLA
    public void pintarBanderas(int nBanderas,List<Object>banderas, int banderaGanadora){    for (int i = 0; i < banderas.size(); i++) {


          for (i = 0; i < banderas.size(); i++) {
              //COGEMOS CADA BANDERA QUE HAY EN LA VISTA Y LE AÑADIMOS LA BANDERA
            Object[] bandera= (Object[]) banderas.get(i);
            String idBandera="bandera"+i;
              int resID = getResources().getIdentifier(idBandera, "id", getPackageName());
              ImageButton imageButton=(ImageButton)findViewById(resID);
              imageButton.setImageResource((Integer) bandera[2]);
              imageButton.setFocusable(false);
              imageButton.setBackgroundColor(Color.TRANSPARENT);
              imageButton.setPadding(10,10,10,10);

              //AÑADIMOS UN EVENTO CLICK PARA CUANDO SEAN PULSADAS
              imageButton.setOnClickListener(new View.OnClickListener() {

                  @Override
                  public void onClick(View view) {
                      //COMPROBAMOS SI HA ACERTADO, SUMAMOS LOS INTENTOS Y PINTAMOS EL FONDO DE LA BANDERA
                     boolean result=comprobarAcierto((Integer) bandera[0],banderaGanadora);
                     intentos++;
                     tintentos.setText("INTENTOS: "+intentos);

                     if(result){
                         imageButton.setBackgroundColor(Color.GREEN);

                     }else{
                         imageButton.setBackgroundColor(Color.RED);
                     }

                  }
              });

              //AÑADIMOS LAS BANDERA
           /* Toast.makeText(this, verBandera[0]+" "+verBandera[1], Toast.LENGTH_SHORT).show();
            System.out.println(verBandera[0]+" "+verBandera[1]);*/
        }


        }

    }

    //METODO PARA PINTAR EL NOMBRE DEL PAIS EN PANTALLA
    public void pintarNombreBandera(String nombreGanadora){
        nombreBandera.setText("¿Cúal es la bandera de "+nombreGanadora+" ?");
    }

    //METODO PARA COMPROBAR SI HAY ACIERTO O FALLO, MODIFICAMOS DATOS DE ACIERTOS, RACHA Y RECORD.
    //DEVOLVEMOS BOOLEAN CON RESULTADO.

    public boolean comprobarAcierto(int miBandera,int banderaGanadora){
        boolean result;
        if(miBandera==banderaGanadora){
            aciertos++;
            rachaAciertos++;
            taciertos.setText("ACIERTOS: "+aciertos);
            trachaAciertos.setText("RACHA ACTUAL: "+rachaAciertos);
            if(record<rachaAciertos){
                //TOAST CON NUEVO RECORD
                Toast.makeText(this,"Nuevo Record: "+rachaAciertos,Toast.LENGTH_SHORT).show();
                record=rachaAciertos;
                trecord.setText("RECORD: "+record);
            }else{
                //TOAST CON RACHA
                Toast.makeText(this,"Racha de Aciertos: "+rachaAciertos,Toast.LENGTH_SHORT).show();
            }
            result=true;

        }else{
            rachaAciertos=0;
            trachaAciertos.setText("RACHA ACTUAL: "+rachaAciertos);
            Toast.makeText(this,"Has fallado",Toast.LENGTH_SHORT).show();
            result=false;
        }


        //GENERAMOS UN THREAD PARA RECARGAR EL PANEL A LOS 3 SEGUNDOS
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(3000);

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                generarJuego(nBanderas);
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ;
        };
        thread.start();

        return result;

    }
}
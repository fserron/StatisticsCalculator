package otaking.com.statisticscalculator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import otaking.com.statisticscalculator.R;
import otaking.com.statisticscalculator.common.components.SeekBarLayout;

public class MainActivity extends AppCompatActivity {

    private static final int MAX_D6 = 5;
    private static final int MAX_D8 = 7;
    private static final int MAX_D10 = 9;
    private static final int MAX_D12 = 11;
    private static final int MAX_D20 = 20;
    private static final int MAX_DADOS = 20;
    private int caras = MAX_D6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.init();
        this.bindearListeners();

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

    //Metodo que inicializa valores
    private void init(){
        final int idTextoAtributo = R.id.editTextAtributos;
        final int idTextoDados = R.id.editTextDados;
        final int idTextoExp = R.id.editTextExplosion;
        final int idSBLayoutExplosion = R.id.seekBarLayoutExplosion;

        //Desactivamos las opciones de explosion.
        EditText editText = (EditText) findViewById(idTextoExp);
        SeekBarLayout sblExplosion = (SeekBarLayout) findViewById(idSBLayoutExplosion);
        SeekBar sbExplosion = sblExplosion.getSeekBar();

        sbExplosion.setEnabled(false);
        editText.setEnabled(false);
        EditText editTextAtributo = (EditText) findViewById(idTextoAtributo);
        EditText editTextDados = (EditText) findViewById(idTextoDados);
        editTextAtributo.setText("1");
        editTextDados.setText("1");
    }

    private void bindearListeners() {
        //Constantes con ids
        final int idTextoAtributo = R.id.editTextAtributos;
        final int idTextoDados = R.id.editTextDados;
        final int idTextoExp = R.id.editTextExplosion;
        final int idCheckExp = R.id.checkBoxExplosion;
        final int idSBLayoutAtributo = R.id.seekBarLayoutAtributos;
        final int idSBLayoutDados = R.id.seekBarLayoutDados;
        final int idSBLayoutExplosion = R.id.seekBarLayoutExplosion;

        //Listener del seekbar de atributos
        SeekBarLayout sblAtributo = (SeekBarLayout) findViewById(idSBLayoutAtributo);
        SeekBar sbAtrib = sblAtributo.getSeekBar();
        sbAtrib.setOnSeekBarChangeListener(seekBarListener(sbAtrib.getId(), idTextoAtributo));

        //Listener del seekbar de dados
        SeekBarLayout sblDados = (SeekBarLayout) findViewById(idSBLayoutDados);
        SeekBar sbDados = sblDados.getSeekBar();
        sbDados.setOnSeekBarChangeListener(seekBarListener(sbDados.getId(), idTextoDados));

        //Listener del seekbar de explosiones
        SeekBarLayout sblExplosion = (SeekBarLayout) findViewById(idSBLayoutExplosion);
        SeekBar sbExplosion = sblExplosion.getSeekBar();
        sbExplosion.setOnSeekBarChangeListener(seekBarListener(sbExplosion.getId(), idTextoExp));

        //Listener del editText de atributos
        EditText etAtrib = (EditText) findViewById(idTextoAtributo);
        etAtrib.setOnEditorActionListener(editTextListener(idSBLayoutAtributo, caras));

        //Listener del editText de dados
        EditText etDados = (EditText) findViewById(idTextoDados);
        etDados.setOnEditorActionListener(editTextListener(idSBLayoutDados, MAX_DADOS));

        //Listener del editText de explosion
        EditText etExp = (EditText) findViewById(idTextoExp);
        etExp.setOnEditorActionListener(editTextListener(idSBLayoutExplosion, caras));

        CheckBox chkExp = (CheckBox) findViewById(idCheckExp);
        chkExp.setOnCheckedChangeListener(checkBoxListener(idSBLayoutExplosion, idTextoExp));

    }

    private SeekBar.OnSeekBarChangeListener seekBarListener(final int source, final int target){
        return new SeekBar.OnSeekBarChangeListener() {
            public void onStopTrackingTouch(SeekBar seekBar) {}

            public void onStartTrackingTouch(SeekBar seekBar) {}

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (seekBar.getId() == source) {
                    EditText editText = (EditText) findViewById(target);
                    editText.setText(String.valueOf(progress + 1));
                }
            }
        };
    }

    private TextView.OnEditorActionListener editTextListener(final int target, final int max){
        return new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String valor = String.valueOf(v.getText());
                Integer valorInt;
                try {
                    valorInt = Integer.valueOf(valor);
                    if (valorInt < 1){
                        v.setText("1");
                    } else if (valorInt > (max)){
                        v.setText(String.valueOf(max));
                    } else {
                        SeekBarLayout seekBarLayout = (SeekBarLayout) findViewById(target);
                        SeekBar seeekBar = seekBarLayout.getSeekBar();
                        seeekBar.setProgress(valorInt - 1);
                    }

                } catch (NumberFormatException nfe){
                    //Loguear?
                }

                return true;
            }
        };
    }

    private CompoundButton.OnCheckedChangeListener checkBoxListener(final int seekbar, final int text){
        return new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
                EditText editText = (EditText) findViewById(text);
                SeekBarLayout seekBarLayout = (SeekBarLayout) findViewById(seekbar);
                SeekBar seeekBar = seekBarLayout.getSeekBar();

                seeekBar.setProgress(0);
                editText.setText("1");

                if (isChecked){
                    seeekBar.setEnabled(true);
                    editText.setEnabled(true);
                } else {
                    seeekBar.setEnabled(false);
                    editText.setEnabled(false);
                }
            }
        };
    }

    public void cambiarDado(View view){
        final int idBotonD6 = R.id.buttonD6;
        final int idBotonD8 = R.id.buttonD8;
        final int idBotonD10 = R.id.buttonD10;
        final int idBotonD12 = R.id.buttonD12;
        final int idBotonD20 = R.id.buttonD20;
        final int idSBLayoutAtributo = R.id.seekBarLayoutAtributos;
        final int idSBLayoutExplosion = R.id.seekBarLayoutExplosion;

        //Listener del seekbar de atributos
        SeekBarLayout sblAtributo = (SeekBarLayout) findViewById(idSBLayoutAtributo);
        SeekBarLayout sblExplosion = (SeekBarLayout) findViewById(idSBLayoutExplosion);

        switch (view.getId()){
            case idBotonD6:
                sblAtributo.resizeSeekBar(MAX_D6);
                sblExplosion.resizeSeekBar(MAX_D6);
                caras = MAX_D6;
                break;
            case idBotonD8:
                sblAtributo.resizeSeekBar(MAX_D8);
                sblExplosion.resizeSeekBar(MAX_D8);
                caras = MAX_D8;
                break;
            case idBotonD10:
                sblAtributo.resizeSeekBar(MAX_D10);
                sblExplosion.resizeSeekBar(MAX_D10);
                caras = MAX_D10;
                break;
            case idBotonD12:
                sblAtributo.resizeSeekBar(MAX_D12);
                sblExplosion.resizeSeekBar(MAX_D12);
                caras = MAX_D12;
                break;
            case idBotonD20:
                sblAtributo.resizeSeekBar(MAX_D20);
                sblExplosion.resizeSeekBar(MAX_D20);
                caras = MAX_D20;
                break;
            default:
                break;
        }
    }

    public void tirar(View view){
        final int idTextoAtributo = R.id.editTextAtributos;
        final int idTextoDados = R.id.editTextDados;
        final int idTextoExp = R.id.editTextExplosion;
        final int idCheckExp = R.id.checkBoxExplosion;

        EditText atributoText = (EditText) findViewById(idTextoAtributo);
        EditText dadosText = (EditText) findViewById(idTextoDados);
        EditText explosionText = (EditText) findViewById(idTextoExp);
        CheckBox chkExp = (CheckBox) findViewById(idCheckExp);

        int atributo = Integer.parseInt(atributoText.getText().toString());
        int dados = Integer.parseInt(dadosText.getText().toString());
        boolean aplica = chkExp.isChecked();
        int explosion = 0;
        if (aplica) explosion = Integer.parseInt(explosionText.getText().toString());

        int carasDado = 6; //Por defecto se tira D6

        //Calculo de la cantidad de caras:
        switch (caras){
            case MAX_D6:
                carasDado = 6;
                break;
            case MAX_D8:
                carasDado = 8;
                break;
            case MAX_D10:
                carasDado = 10;
                break;
            case MAX_D12:
                carasDado = 12;
                break;
            case MAX_D20:
                carasDado = 20;
                break;
        }

        Intent resultsActivity = new Intent(this, ResultsActivity.class);
        resultsActivity.putExtra("atributo", atributo);
        resultsActivity.putExtra("dados", dados);
        resultsActivity.putExtra("explosion", explosion);
        resultsActivity.putExtra("aplica", aplica);
        resultsActivity.putExtra("caras", carasDado);
        startActivity(resultsActivity);
    }
}

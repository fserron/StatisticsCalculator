package otaking.com.statisticscalculator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
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

public class MainActivity extends AppCompatActivity {

    private static final Integer MAX_D6 = 4;
    private static final Integer MAX_D8 = 6;
    private static final Integer MAX_D10 = 8;
    private static final Integer MAX_D12 = 10;
    private static final Integer MAX_D20 = 19;
    private Integer caras = MAX_D6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
        final int idBarraAtributo = R.id.seekBarAtributos;
        final int idTextoAtributo = R.id.editTextAtributos;
        final int idBarraDados = R.id.seekBarDados;
        final int idTextoDados = R.id.editTextDados;
        final int idBarraExp = R.id.seekBarExplosion;
        final int idTextoExp = R.id.editTextExplosion;
        final int idCheckExp = R.id.checkBoxExplosion;

        //Desactivamos las opciones de explosion.
        EditText editText = (EditText) findViewById(idTextoExp);
        SeekBar seeekBar = (SeekBar) findViewById(idBarraExp);

        seeekBar.setEnabled(false);
        //editText.setInputType(InputType.TYPE_NULL);
        //editText.setFocusable(false);
        editText.setEnabled(false);
    }

    private void bindearListeners() {
        //Constantes con ids
        final int idBarraAtributo = R.id.seekBarAtributos;
        final int idTextoAtributo = R.id.editTextAtributos;
        final int idBarraDados = R.id.seekBarDados;
        final int idTextoDados = R.id.editTextDados;
        final int idBarraExp = R.id.seekBarExplosion;
        final int idTextoExp = R.id.editTextExplosion;
        final int idCheckExp = R.id.checkBoxExplosion;

        //Listener del seekbar de atributos
        SeekBar sbAtrib = (SeekBar) findViewById(idBarraAtributo);
        sbAtrib.setOnSeekBarChangeListener(seekBarListener(idBarraAtributo, idTextoAtributo));

        //Listener del seekbar de dados
        SeekBar sbDados = (SeekBar) findViewById(idBarraDados);
        sbDados.setOnSeekBarChangeListener(seekBarListener(idBarraDados, idTextoDados));

        //Listener del seekbar de dados
        SeekBar sbExp = (SeekBar) findViewById(idBarraExp);
        sbExp.setOnSeekBarChangeListener(seekBarListener(idBarraExp, idTextoExp));

        //Listener del editText de atributos
        EditText etAtrib = (EditText) findViewById(idTextoAtributo);
        etAtrib.setOnEditorActionListener(editTextListener(idBarraAtributo));

        //Listener del editText de dados
        EditText etDados = (EditText) findViewById(idTextoDados);
        etDados.setOnEditorActionListener(editTextListener(idBarraDados));

        //Listener del editText de dados
        EditText etExp = (EditText) findViewById(idTextoExp);
        etExp.setOnEditorActionListener(editTextListener(idBarraExp));

        CheckBox chkExp = (CheckBox) findViewById(idCheckExp);
        chkExp.setOnCheckedChangeListener(checkBoxListener(idBarraExp, idTextoExp));

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

    private TextView.OnEditorActionListener editTextListener(final int target){
        return new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String valor = String.valueOf(v.getText());
                Integer valorInt;
                try {
                    valorInt = Integer.valueOf(valor);
                    if (valorInt < 1 || valorInt > (caras + 1)) //TODO: Obtener cant caras
                        v.setText("");
                    else {
                        SeekBar seeekBar = (SeekBar) findViewById(target);
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
                SeekBar seeekBar = (SeekBar) findViewById(seekbar);

                seeekBar.setProgress(0);
                editText.setText("");

                if (isChecked){
                    seeekBar.setEnabled(true);
                    //editText.setFocusable(true);
                    //editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                    editText.setEnabled(true);
                } else {
                    seeekBar.setEnabled(false);
                    //editText.setInputType(InputType.TYPE_NULL);
                    //editText.setFocusable(false);
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
        final int idBarraAtributo = R.id.seekBarAtributos;
        final int idBarraExp = R.id.seekBarExplosion;
        final int idIndicesAtrib = R.id.textViewIndicesAtributos;
        final int idIndicesExp = R.id.textViewIndicesAtributos;

        SeekBar sbAtrib = (SeekBar) findViewById(idBarraAtributo);
        SeekBar sbExp = (SeekBar) findViewById(idBarraExp);
        TextView textIndiceA = (TextView) findViewById(idIndicesAtrib);
        TextView textIndiceE = (TextView) findViewById(idIndicesExp);

        if (view.getId() == idBotonD6){
            sbAtrib.setMax(MAX_D6);
            sbExp.setMax(MAX_D6);
            textIndiceA.setText(R.string.marcasD6);
            textIndiceE.setText(R.string.marcasD6);
            caras = MAX_D6;
        } else if (view.getId() == idBotonD8){
            sbAtrib.setMax(MAX_D8);
            sbExp.setMax(MAX_D8);
            textIndiceA.setText(R.string.marcasD8);
            textIndiceE.setText(R.string.marcasD8);
            caras = MAX_D8;
        } else if (view.getId() == idBotonD10){
            sbAtrib.setMax(MAX_D10);
            sbExp.setMax(MAX_D10);
            textIndiceA.setText(R.string.marcasD10);
            textIndiceE.setText(R.string.marcasD10);
            caras = MAX_D10;
        } else if (view.getId() == idBotonD12){
            sbAtrib.setMax(MAX_D12);
            sbExp.setMax(MAX_D12);
            textIndiceA.setText(R.string.marcasD12);
            textIndiceE.setText(R.string.marcasD12);
            caras = MAX_D12;
        } else if (view.getId() == idBotonD20){
            sbAtrib.setMax(MAX_D20);
            sbExp.setMax(MAX_D20);
            textIndiceA.setText(R.string.marcasD20);
            textIndiceE.setText(R.string.marcasD20);
            caras = MAX_D20;
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

        int caras = 6; //Por defecto se tira D6

        //Calculo de la cantidad de caras:
        if (atributo > 5 && atributo <= 7) caras = 8;
        else if (atributo > 7 && atributo <= 9) caras = 10;
        else if (atributo > 9 && atributo <= 11) caras = 12;
        else if (atributo > 11 && atributo <= 20) caras = 20;

        Intent resultsActivity = new Intent(this, ResultsActivity.class);
        resultsActivity.putExtra("atributo", atributo);
        resultsActivity.putExtra("dados", dados);
        resultsActivity.putExtra("explosion", explosion);
        resultsActivity.putExtra("aplica", aplica);
        resultsActivity.putExtra("caras", caras);
        startActivity(resultsActivity);
    }
}
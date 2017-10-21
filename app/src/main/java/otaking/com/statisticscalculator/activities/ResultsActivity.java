package otaking.com.statisticscalculator.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import otaking.com.statisticscalculator.R;
import otaking.com.statisticscalculator.backend.bo.EstadisticaBO;
import otaking.com.statisticscalculator.backend.bo.impl.EstadisticaBOImpl;
import otaking.com.statisticscalculator.common.Utils;
import otaking.com.statisticscalculator.entities.dto.EstadisticaDTO;
import otaking.com.statisticscalculator.entities.dto.TiradaDTO;

public class ResultsActivity extends AppCompatActivity {

    private EstadisticaBO estadisticaBO;
    private List<Entry> valoresEjeY;
    private List<String> valoresEjeX;

    private static final int TAMANO_MUESTRA = 1000000;
    private static final int TAMANO_MUESTRA_EXPLOSION = 100000;
    private static final int CANT_PASTEL = 5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        estadisticaBO = new EstadisticaBOImpl();

        init();
    }

    private void init(){
        final int idTextoMaximo = R.id.textViewValorMaximo;
        final int idTextoMinimo = R.id.textViewValorMinimo;
        final int idTextoPromedio = R.id.textViewValorPromedio;
        final int idPastel = R.id.chartPastel;
        final int idLineas = R.id.chartLineas;
        final int idBotonLineas = R.id.buttonLineas;

        LineChart graficoLineas = (LineChart) findViewById(idLineas);
        PieChart graficoPastel = (PieChart) findViewById(idPastel);
        Button botonLineas = (Button) findViewById(idBotonLineas);

        graficoPastel.setVisibility(View.GONE);
        botonLineas.setEnabled(false);

        Intent mainIntent = getIntent();
        Integer atributo = mainIntent.getIntExtra("atributo", 1);
        Integer dados = mainIntent.getIntExtra("dados", 0);
        Integer explosion = mainIntent.getIntExtra("explosion", 0);
        Boolean aplica =  mainIntent.getBooleanExtra("aplica", false);
        Integer caras = mainIntent.getIntExtra("caras", 6);

        TiradaDTO dto = new TiradaDTO();
        dto.setAtributo(atributo);
        dto.setDados(dados);
        dto.setSuerte(explosion);
        dto.setExplosiva(aplica);
        dto.setCaras(caras);

        //Atributos que se rellenan porque son necesarios, aunque no entran en juego todavia
        dto.setBono(0);
        dto.setFatidica(false);

        //Definimos el tamaño de la muestra
        int muestra = TAMANO_MUESTRA;
        if (aplica) muestra = TAMANO_MUESTRA_EXPLOSION; //Si aplica explosion, reducimos la muestra

        //Se hace la tirada
        EstadisticaDTO estadisticaDTO = estadisticaBO.estadisticaTirada(dto, muestra);

        TextView maximoText = (TextView) findViewById(idTextoMaximo);
        TextView minimoText = (TextView) findViewById(idTextoMinimo);
        TextView promedioText = (TextView) findViewById(idTextoPromedio);

        maximoText.setText(estadisticaDTO.getMaximo().toString());
        minimoText.setText(estadisticaDTO.getMinimo().toString());
        promedioText.setText(estadisticaDTO.getPromedio());

        //Preparacion de datos para el grafico
        Map<Integer, Integer> mapa = estadisticaDTO.getMapaResultados();

        valoresEjeY = new ArrayList<>();
        valoresEjeX = new ArrayList<>();

        Legend l = graficoLineas.getLegend();
        l.setForm(Legend.LegendForm.LINE);

        graficoLineas.setData(this.armarGraficoLineas(mapa));
        graficoPastel.setData(this.armarGraficoPastel(mapa));

        graficoLineas.invalidate(); //refresh
        graficoPastel.invalidate(); //refresh
    }

    public void graficoLineas(View view){
        final int idPastel = R.id.chartPastel;
        final int idLineas = R.id.chartLineas;
        final int idBotonLineas = R.id.buttonLineas;
        final int idBotonPastel = R.id.buttonPastel;

        LineChart graficoLineas = (LineChart) findViewById(idLineas);
        PieChart graficoPastel = (PieChart) findViewById(idPastel);
        Button botonLineas = (Button) findViewById(idBotonLineas);
        Button botonPastel = (Button) findViewById(idBotonPastel);

        graficoPastel.setVisibility(View.GONE);
        graficoLineas.setVisibility(View.VISIBLE);
        botonLineas.setEnabled(false);
        botonPastel.setEnabled(true);
    }

    public void graficoPastel(View view){
        final int idPastel = R.id.chartPastel;
        final int idLineas = R.id.chartLineas;
        final int idBotonLineas = R.id.buttonLineas;
        final int idBotonPastel = R.id.buttonPastel;

        LineChart graficoLineas = (LineChart) findViewById(idLineas);
        PieChart graficoPastel = (PieChart) findViewById(idPastel);
        Button botonLineas = (Button) findViewById(idBotonLineas);
        Button botonPastel = (Button) findViewById(idBotonPastel);

        graficoLineas.setVisibility(View.GONE);
        graficoPastel.setVisibility(View.VISIBLE);
        botonLineas.setEnabled(true);
        botonPastel.setEnabled(false);
    }

    private LineData armarGraficoLineas(Map<Integer, Integer> mapa){
        Iterator it = mapa.keySet().iterator();

        //Armado de los datos de las lineas
        List<Entry> lineEntries = new ArrayList<>();

        //Procesamiento de los graficos
        while (it.hasNext()) {
            Integer indice = (Integer) it.next();
            Integer ocurrencias = mapa.get(indice);
            valoresEjeY.add(new Entry(indice, ocurrencias));
            valoresEjeX.add(indice.toString());
        }

        // create a dataset and give it a type
        LineDataSet lineDataSet = new LineDataSet(valoresEjeY, "Cantidad de tiradas con determinada puntuacion");
        lineDataSet.setFillAlpha(110);
        lineDataSet.setColor(Color.BLACK);
        lineDataSet.setCircleColor(Color.BLACK);
        lineDataSet.setLineWidth(1f);
        lineDataSet.setCircleRadius(3f);
        lineDataSet.setDrawCircleHole(false);
        lineDataSet.setValueTextSize(9f);
        lineDataSet.setDrawFilled(true);
        //lineDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        //ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        //dataSets.add(lineDataSet);
        //LineData data = new LineData(valoresEjeX, dataSets);

        LineData data = new LineData();
        data.addDataSet(lineDataSet);

        return new LineData(lineDataSet);
    }

    private PieData armarGraficoPastel(Map<Integer, Integer> mapa){
        Map<String, Integer> mapaNuevo = agruparResultados(mapa);

        //Armado de los datos del pastel
        List<PieEntry> pastelEntries = new ArrayList<>();

        Iterator it = mapaNuevo.keySet().iterator();

        //Procesamiento de los graficos
        while (it.hasNext() && pastelEntries.size() < CANT_PASTEL) {
            String indice = (String) it.next();
            String leyenda = indice + " pts.";
            Float porcentaje = (float) mapaNuevo.get(indice) / TAMANO_MUESTRA;
            pastelEntries.add(new PieEntry(porcentaje * 100, leyenda));
        }

        PieDataSet pieDataSet = new PieDataSet(pastelEntries, "");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        return new PieData(pieDataSet);
    }

    private static Map<String, Integer> agruparResultados(Map<Integer, Integer> mapa){
        Map<String, Integer> nuevoMapa = new TreeMap<>();

        //Armamos la separacion en grupos
        int resultados = mapa.keySet().size();
        int resto = resultados % CANT_PASTEL;
        int totalGrupo = 0;
        double cantPorGrupo;
        int digitos = 1;

        int ultimoIndice = (Integer) mapa.keySet().toArray()[mapa.keySet().size() - 1];
        if (ultimoIndice >= 100) digitos = 3;
        else if (ultimoIndice >= 10) digitos = 2;

        //Si la cantidad de resultados...
        if (resto == 0) {//Es un multiplo exacto de la cantidad de grupos
            cantPorGrupo = mapa.keySet().size() / CANT_PASTEL;
        } else if (resultados <= CANT_PASTEL) { //Es menor a la cantidad de grupos
            //Transformamos el mapa
            for (Integer indice : mapa.keySet()) {
                nuevoMapa.put(String.valueOf(indice), mapa.get(indice));
            }

            return nuevoMapa;
        } else {
            //Si son mas de la cantidad de grupos, redondeamos hacia abajo
            cantPorGrupo = Math.floor((double) mapa.keySet().size() / CANT_PASTEL);
        }

        Iterator it = mapa.keySet().iterator();

        int index = 1;
        String clave = null;

        while (it.hasNext()){
            Integer key = (Integer) it.next();

            //Voy sumando los valores
            totalGrupo += mapa.get(key);

            int var = 0;
            switch (resto){
                case 1: //Si solo sobra uno, lo agregamos al principio
                    if (nuevoMapa.size() == 0) var = 1;
                    else var = 0;
                    break;
                case 2: //Si sobran dos, agregamos uno al principio y otro al final
                    if (nuevoMapa.size() == 0) var = 1;
                    else if (nuevoMapa.size() == (CANT_PASTEL - 1)) var = 1;
                    else var = 0;
                    break;
                case 3: //Si sobran tres, agregamos dos al principio y uno al final
                    if (nuevoMapa.size() == 0) var = 2;
                    else if (nuevoMapa.size() == (CANT_PASTEL - 1)) var = 1;
                    else var = 0;
                    break;
                case 4: //Si sobran cuatro, agregamos dos al principio y dos al final
                    if (nuevoMapa.size() == 0) var = 2;
                    else if (nuevoMapa.size() == (CANT_PASTEL - 1)) var = 2;
                    else var = 0;
                    break;
            }

            //Registro el primer elemento
            if (index == 1) clave = Utils.zeroPad(key.toString(), digitos);
            //Registro el ultimo elemento
            if (index % (cantPorGrupo + var) == 0) {
                clave += " a " + Utils.zeroPad(key.toString(), digitos);
                nuevoMapa.put(clave, totalGrupo);
                totalGrupo = 0;
                index = 1;
            } else {
                index++;
            }
        }

        return nuevoMapa;
    }

    private static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}

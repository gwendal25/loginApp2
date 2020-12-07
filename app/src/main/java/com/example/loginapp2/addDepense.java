package com.example.loginapp2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class addDepense extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    RadioGroup depenseGroup;
    EditText valeur_euros;
    Spinner depensesSpinner;
    EditText description;
    Button makeTransactionButton;

    String itemDropDown = "";
    String itemRadio = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_depense);

        initViews();
        makeCashFlowList(R.array.gainCategorie);
        initDepenseGroup();
        initTransactionButton();
    }



    public void initViews(){
        depenseGroup = findViewById(R.id.radioGroup);
        valeur_euros = findViewById(R.id.valeur_euros_input);
        depensesSpinner = findViewById(R.id.spDepenses);
        description = findViewById(R.id.desc_input);
        makeTransactionButton = findViewById(R.id.makeTransaction);
    }

    public void makeCashFlowList(int arrayId){
        depensesSpinner.setOnItemSelectedListener(this);
        //make an adapter with the array and the default layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, arrayId, android.R.layout.simple_spinner_item);
        //specify the layout for the items
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        depensesSpinner.setAdapter(adapter);
    }

    public void initDepenseGroup(){
        depenseGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if(checkedId == R.id.gain_button){
                itemRadio = "gain";
                makeCashFlowList(R.array.gainCategorie);
            }
            else if(checkedId == R.id.depense_button){
                itemRadio = "depense";
                makeCashFlowList(R.array.depenseCategorie);
            }
        });
    }

    public void initTransactionButton(){
        makeTransactionButton.setOnClickListener(v -> {

            String currentDate = makeCurrentDate();
            String descriptionText = description.getText().toString().trim();
            int valeur = getCurrentValue();
            if(valeur == 0){
                return;
            }

            DBCashHelper dataHelper = new DBCashHelper(addDepense.this);

            switch(itemRadio){
                case "gain":
                    dataHelper.addGain(currentDate, valeur, itemDropDown, descriptionText);
                    break;
                case "depense":
                    dataHelper.addDepense(currentDate, valeur, itemDropDown, descriptionText);
                    break;
                default:
                    Toast.makeText(addDepense.this, "pas de type de dépense sélectionnné", Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    public String makeCurrentDate(){
        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        String currentDate = sdf.format(currentTime);
        return currentDate;
    }

    public int getCurrentValue(){
        String valeurString = valeur_euros.getText().toString().trim();
        if(valeurString.isEmpty()){
            Toast.makeText(addDepense.this, "valeur en euros vide", Toast.LENGTH_SHORT).show();
            return 0;
        }
        int valeur = Integer.valueOf(valeurString);
        return valeur;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        itemDropDown = (String) parent.getItemAtPosition(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
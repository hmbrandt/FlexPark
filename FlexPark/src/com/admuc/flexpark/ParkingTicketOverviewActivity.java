package com.admuc.flexpark;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ParkingTicketOverviewActivity extends Activity {

   private MyItemAdapter myAdapter;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.parkingticketoverview);
      initDatensaetze();
      ListView l = (ListView) findViewById(R.id.pto_ListView);
      myAdapter = new MyItemAdapter();
      l.setAdapter(myAdapter);
      l.setOnItemClickListener(myAdapter);
   }

   @Override
   protected void onResume() {
      super.onResume();
      // falls wir vom Änderungsformular zurückkommen...
      myAdapter.notifyDataSetChanged();
   }

   public class Datensatz {
      public String name; // besser setter und getter-Methoden schreiben, stört hier aber...
      public String fee; // die Umwandlung von Datum lasse ich weg - das ist ein anderes (großes) Problem

      public Datensatz(String name, String datum) {
         this.name = name;
         this.fee = datum;
      }
   }

   private ArrayList<Datensatz> datensaetze;

   private void initDatensaetze() {
      datensaetze = new ArrayList<Datensatz>();
      Datensatz datensatz1 = new Datensatz("Altmarkt", "2,30");
      Datensatz datensatz2 = new Datensatz("Postplatz", "5,48");
      Datensatz datensatz3 = new Datensatz("TU", "0,50");
      datensaetze.add(datensatz1);
      datensaetze.add(datensatz2);
      datensaetze.add(datensatz3);

   }

   class MyItemAdapter extends BaseAdapter implements OnItemClickListener {
      private final LayoutInflater mInflater;

      public MyItemAdapter() {
         mInflater = (LayoutInflater) ParkingTicketOverviewActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      }

      public int getCount() {
         return datensaetze.size();
      }

      public Datensatz getItem(int position) {
         return datensaetze.get(position);
      }

      public long getItemId(int position) {
         return (long) position;
      }

      public View getView(int position, View convertView, ViewGroup parent) {
         LinearLayout itemView = (LinearLayout) mInflater.inflate(R.layout.parkingticketoverview_listitem, parent, false);
         bindView(itemView, position);
         return itemView;
      }

      private void bindView(LinearLayout view, int position) {
         Datensatz datensatz = getItem(position);
         view.setId((int) getItemId(position));
         TextView nameTextView = (TextView) view.findViewById(R.id.pto_ListElementParkingSpace);
         TextView feeTextView = (TextView) view.findViewById(R.id.pto_ListElementTotalFee);
         nameTextView.setText(datensatz.name);
         feeTextView.setText(datensatz.fee);
      }

      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         // Meldung ausgeben oder Intent bauen und Activity starten
         Datensatz gewaehlterDatensatz = datensaetze.get(position);
      }
   }

}

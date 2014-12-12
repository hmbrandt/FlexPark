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
import android.widget.ListView;
import android.widget.RelativeLayout;
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
      myAdapter.notifyDataSetChanged();
   }

   public class Datensatz {
      public String psname;
      public String startime;
      public String bookedtime;
      public String resttime;
      public String totalfee;

      public Datensatz(String psname, String startime, String bookedtime, String resttime, String totalfee) {
         this.psname = psname;
         this.startime = startime;
         this.bookedtime = bookedtime;
         this.resttime = resttime;
         this.totalfee = totalfee;
      }
   }

   private ArrayList<Datensatz> datensaetze;

   private void initDatensaetze() {
      datensaetze = new ArrayList<Datensatz>();
      Datensatz datensatz1 = new Datensatz("Altmarkt", "05:23", "06:23", "00:20", "02,30 �");
      Datensatz datensatz2 = new Datensatz("Postplatz", "11:30", "15:15", "02:12", "08,50 �");
      Datensatz datensatz3 = new Datensatz("TU Dresden", "17:45", "19:00", "00:30", "01,10 �");
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
         RelativeLayout itemView = (RelativeLayout) mInflater.inflate(R.layout.parkingticketoverview_listitem, parent, false);
         bindView(itemView, position);
         return itemView;
      }

      private void bindView(RelativeLayout view, int position) {
         Datensatz datensatz = getItem(position);
         view.setId((int) getItemId(position));
         TextView psnameTextView = (TextView) view.findViewById(R.id.pto_ListElementParkingSpaceOutput);
         TextView startimeTextView = (TextView) view.findViewById(R.id.pto_ListElementStartTimeOutput);
         TextView bookedtimeTextView = (TextView) view.findViewById(R.id.pto_ListElementBookedTimeOutput);
         TextView resttimeTextView = (TextView) view.findViewById(R.id.pto_ListElementRestTimeOutput);
         TextView totalfeeTextView = (TextView) view.findViewById(R.id.pto_ListElementTotalFeeOutput);
         psnameTextView.setText(datensatz.psname);
         startimeTextView.setText(datensatz.startime);
         bookedtimeTextView.setText(datensatz.bookedtime);
         resttimeTextView.setText(datensatz.resttime);
         totalfeeTextView.setText(datensatz.totalfee);
      }

      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
         Datensatz gewaehlterDatensatz = datensaetze.get(position);
      }
   }

}

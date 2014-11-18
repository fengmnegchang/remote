package com.fgj.urose;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
 
public class UmeiActivity extends Activity{
	private ListView listview;
	private UmeiAdapter adapter;
	
	private static Bitmap bitmap;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); 
        setContentView(R.layout.activity_umei);
        
        listview = (ListView) findViewById(R.id.list);
        
    }

    
    private class UmeiAdapter extends BaseAdapter{
		@Override
		public int getCount() {
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int arg0, View arg1, ViewGroup arg2) {
			LayoutInflater inflater = LayoutInflater.from(getApplicationContext());
			View view = null; 
			ViewHolder hodler;
			if(view == null){
				view = inflater.inflate(R.layout.item_list, null);
				hodler = new ViewHolder();
				hodler.img = (ImageView) view.findViewById(R.id.img);
				hodler.name = (TextView) view.findViewById(R.id.name);
				
				view.setTag(hodler);
			}else{
				hodler = (ViewHolder) view.getTag();
			}
			
			return view;
		}
    	
		private class ViewHolder{
			ImageView img;
			TextView name;
		}
    }
    
    private static class UmeiAsyncTask extends AsyncTask<String,Integer,Bitmap>{
		ImageView img;
		Context context;
		String localResName;
		
		public UmeiAsyncTask(ImageView img,Context context,String localResName){
			this.img = img;
			this.context = context;
			this.localResName = localResName;
		}
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			img.setImageBitmap(null);
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			Message msg = new Message();
			if(result != null) {
				bitmap = result;
				img.setImageBitmap(bitmap);
				msg.what = 1;
            }else {
            	msg.what = 0;
            	int resId = context.getResources().getIdentifier(localResName, 
    					"drawable",
    					context.getPackageName());
            	img.setImageResource(resId);
            }
			handleMessage(msg);
		}
		
		protected void handleMessage(Message msg){
			
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
		}

		@Override
		protected void onCancelled(Bitmap result) {
			super.onCancelled(result);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			final Bitmap bm;
			try {
				HttpClient hc = new DefaultHttpClient();
	            HttpGet hg = new HttpGet(params[0]); 
                HttpResponse hr = hc.execute(hg);
                bm = BitmapFactory.decodeStream(hr.getEntity().getContent());
//                menuDrawable = Drawable.createFromStream(hr.getEntity().getContent(), "");
            } catch (Exception e) {
                return null;
            }
            return bm;
		}
	}
     
 
}

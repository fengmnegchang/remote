package com.fgj.urose.umei;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fgj.urose.R;
import com.fgj.urose.remote.sax.entity.Waterfalls;
import com.fgj.urose.remote.sax.ksoap.WaterfallService;

public class UmeiFragment extends Fragment {
	private ListView listview;
	private Drawable bitmap;
	private UmeiAsyncTask umeiTask;
	private WaterfallService waterfallService;
	private Waterfalls falls;
	private UmeiAdapter umeiAdapter;
	
	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			if(msg.what==1000){
				umeiAdapter = new UmeiAdapter(falls);
		        listview.setAdapter(umeiAdapter);
		        listview.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View view,
							int arg2, long arg3) {
						UmeiAdapter.ViewHolder holder = (com.fgj.urose.umei.UmeiFragment.UmeiAdapter.ViewHolder) view.getTag();
						Intent intent = new Intent();
						intent.putExtra("gaoqingurl", holder.subUrl.getText().toString());
						intent.setClass(getActivity().getApplicationContext(), 
								UmeiGaoqingActivity.class);
						getActivity().startActivity(intent);
					}
				});
			}
			super.handleMessage(msg);
		}
		
	};
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_umei_tab, container,
				false);
		listview = (ListView) rootView.findViewById(R.id.listview);
		AsyncTask.execute(new Runnable(){
			@Override
			public void run() {
				waterfallService = new WaterfallService(getActivity().getApplicationContext());
		        falls = waterfallService.getUmeiWaters();
		        if(falls.getFalls()==null){
		        	falls = waterfallService.getWaters();
		        }
		        handler.sendEmptyMessage(1000);
			}
		});
		return rootView;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
	}

	private class UmeiAdapter extends BaseAdapter {
		Waterfalls falls;
		public UmeiAdapter(Waterfalls falls) {
			this.falls = falls;
		}

		@Override
		public int getCount() {
			if(falls.getFalls() ==null){
				return 0;
			}
			return falls.getFalls().size();
		}

		@Override
		public Object getItem(int arg0) {
			if(falls.getFalls() ==null){
				return null;
			}
			return falls.getFalls().get(arg0);
		}

		@Override
		public long getItemId(int arg0) {
			return arg0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewHolder = null;
			if (convertView == null) {
				LayoutInflater inflater = LayoutInflater
						.from(getActivity().getApplicationContext());
				convertView = inflater.inflate(R.layout.item_list_view, null);
				viewHolder = new ViewHolder();
				viewHolder.img = (ImageView) convertView.findViewById(R.id.img);
				viewHolder.title = (TextView) convertView.findViewById(R.id.title);
				viewHolder.subUrl = (TextView) convertView.findViewById(R.id.subUrl);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			// viewHolder.img.setImageResource(icons[position]);
			// viewHolder.title.setText(menus[position]);
			umeiTask = new UmeiAsyncTask(viewHolder.img, 
					getActivity().getApplicationContext(), 
					falls.getFalls().get(position).getImageName());
			umeiTask.execute(falls.getFalls().get(position).getUrl());
			viewHolder.title.setText(falls.getFalls().get(position).getTitle());
			viewHolder.subUrl.setText(falls.getFalls().get(position).getSubUrl());
			return convertView;
		}
		private class ViewHolder {
			ImageView img;
			TextView title;
			TextView subUrl;
		}

	}

	private  class UmeiAsyncTask extends
			AsyncTask<String, Integer, Drawable> {
		ImageView img;
		Context context;
		String localResName;

		public UmeiAsyncTask(ImageView img, Context context, String localResName) {
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
		protected void onPostExecute(Drawable result) {
			super.onPostExecute(result);
			Message msg = new Message();
			if (result != null) {
				bitmap = result;
				img.setBackground(bitmap);
				msg.what = 1;
			} else {
				msg.what = 0;
//				int resId = context.getResources().getIdentifier(localResName,
//						"drawable", context.getPackageName());
				try {
					InputStream inputStream = context.getResources().getAssets().open("waterfall/"
							+ localResName);
					Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
					Drawable drawable =new BitmapDrawable(bitmap);
					img.setBackground(drawable);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			handleMessage(msg);
		}

		protected void handleMessage(Message msg) {

		}

		@Override
		protected void onProgressUpdate(Integer... values) {
		}

		@Override
		protected void onCancelled(Drawable result) {
			super.onCancelled(result);
		}

		@Override
		protected void onCancelled() {
			super.onCancelled();
		}

		@Override
		protected Drawable doInBackground(String... params) {
			final Drawable bm;
			try {
				HttpClient hc = new DefaultHttpClient();
				HttpGet hg = new HttpGet(params[0]);
				HttpResponse hr = hc.execute(hg);
//				bm = BitmapFactory.decodeStream(hr.getEntity().getContent());
				// menuDrawable =
				bm = Drawable.createFromStream(hr.getEntity().getContent(), "");
			} catch (Exception e) {
				return null;
			}
			return bm;
		}
	}

}

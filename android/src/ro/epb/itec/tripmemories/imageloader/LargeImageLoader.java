package ro.epb.itec.tripmemories.imageloader;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;

import ro.epb.itec.tripmemories.ui.view.TouchImageView;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Picasso.LoadedFrom;
import com.squareup.picasso.Target;

public class LargeImageLoader {

	private static Executor serialExecuter = new SerialExecutor();

	public static void load(final Context context, final File file, final TouchImageView view){
		
		
		AsyncTask<Void, Void, Bitmap> loadTask = new AsyncTask<Void, Void, Bitmap>() {
			Bitmap rez;
			@Override
			protected Bitmap doInBackground(Void... params) {
				final CountDownLatch latch = new CountDownLatch(1);
				
				Picasso.with(context).load(file).skipMemoryCache().resize(100, 100).into(new Target() {
					
					@Override
					public void onPrepareLoad(Drawable placeHolderDrawable) {
						latch.countDown();
					}
					
					@Override
					public void onBitmapLoaded(Bitmap bitmap, LoadedFrom from) {
						rez = bitmap;
						latch.countDown();
						
					}
					
					@Override
					public void onBitmapFailed(Drawable errorDrawable) { 
						latch.countDown();
					}
				});
				
				try {
					latch.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return rez;
			}
			
			@Override
		    protected void onPostExecute(Bitmap result) {
				if(result != null)
					view.setImageBitmap(result);
		    }
		};
		
		loadTask.executeOnExecutor(serialExecuter);
		
//			TouchImageView view) {
////		Picasso.with(getActivity()).load(new File(imagePath)).skipMemoryCache().into(view);
//			Picasso.with(getActivity()).load(new File(imagePath)).skipMemoryCache().into(new Target() {
//				
//				@Override
//				public void onPrepareLoad(Drawable placeHolderDrawable) {
//					Log.i("PREP", "PREP");
//				}
//				
//				@Override
//				public void onBitmapLoaded(Bitmap bitmap, LoadedFrom from) {
//					view.setImageBitmap(bitmap);
//					Log.i("WIN", "WIN");
//				}
//				
//				@Override
//				public void onBitmapFailed(Drawable errorDrawable) {
//					Log.i("FAIL", "FAIL");
//				}
//			});
////			
////			Runnable run = new Runnable() {
////				
////				@Override
////				public void run() {
////					Picasso.with(getActivity()).load(new File(imagePath)).fit().noFade().into(view);
////					view.postDelayed(this, 2000);
////				}
////			};
////			view.postDelayed(run, 2000);
	}

}

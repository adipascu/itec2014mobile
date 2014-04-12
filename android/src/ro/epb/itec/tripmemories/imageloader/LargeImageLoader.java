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

	private static Executor serialExecuter;
//TODO: add these prefs
	// column count ovveride
	//hd mode
	//sd mode set resolution
	public static void load(final Context context, final File file, final TouchImageView view){
		boolean hdMode = false;
		if(hdMode){
			loadHD(context, file, view);
		}
		else{
			loadSD(context, file, view, 1000);
		}
	}

	private static void loadSD(Context context, File file, TouchImageView view, int size) {
		Picasso.with(context).load(file).resize(size, size).centerInside().into(view);
	}

	private static void loadHD(final Context context, final File file, final TouchImageView view) {
		AsyncTask<Void, Void, Bitmap> loadTask = new AsyncTask<Void, Void, Bitmap>() {
			Bitmap rez;
			@Override
			protected Bitmap doInBackground(Void... params) {
				final CountDownLatch latch = new CountDownLatch(1);
				Picasso.with(context).load(file).skipMemoryCache().into(new Target() {

					@Override
					public void onPrepareLoad(Drawable placeHolderDrawable) {
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
		if(serialExecuter== null)
			serialExecuter = new SerialExecutor();
		loadTask.executeOnExecutor(serialExecuter);

	}

}

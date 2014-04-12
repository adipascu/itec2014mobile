package ro.epb.itec.tripmemories.ui;

import java.io.File;

import ro.epb.itec.tripmemories.ui.view.TouchImageView;
import android.content.Context;

import com.squareup.picasso.Picasso;

public class LargeImageLoader {

	public static void load(Context context, File file, TouchImageView view){
		Picasso.with(context).load(file).skipMemoryCache().resize(100, 100).into(view);
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

/**
 * @author Anthony DeDominic <anthony@dedominic.pw>
 * @licence GPLv3
 */
package pw.dedominic.csc311_final;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainMenuActivity extends Activity implements View.OnTouchListener, View.OnClickListener
{
	// the map view and the image matrix that describes translational movement
	private ImageView mImageView;
	private float xDelta;
	private float yDelta;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map);
		mImageView = (ImageView) findViewById(R.id.imageView);
		mImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.blank));
		mImageView.setOnTouchListener(this);
	}

	/**
	 * Handles the dragging of the map view.
	 * Dragging will trigger the function to modify
	 * the location of the image in the view.
	 * xDelta and yDelta are globals as their value
	 * needs to be preserved
	 *
	 * @param v the map image being dragged
	 * @param e the event that is being handled
	 * @return meaningless
	 */
	@Override
	public boolean onTouch(View v, MotionEvent e)
	{
		float x = e.getRawX();
		float y = e.getRawY();

		switch (e.getAction() & MotionEvent.ACTION_MASK)
		{
			case MotionEvent.ACTION_DOWN:
				xDelta = (x - mImageView.getTranslationX());
				yDelta = (y - mImageView.getTranslationY());
				break;
			case MotionEvent.ACTION_MOVE:
				mImageView.setTranslationX(x - xDelta);
				mImageView.setTranslationY(y - yDelta);
				break;
		}
		return true;
	}

	/**
	 * Function that handles clicking of users on the map view.
	 * Should draw basic information about the user.
	 *
	 * @param v View that was clicked by user
	 */
	@Override
	public void onClick(View v)
	{
		return;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings)
		{
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}

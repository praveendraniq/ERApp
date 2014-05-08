package org.sjsucmpe131.erapp;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.parse.ParseObject;

public class AddExpense extends Activity {
	ParseObject expenseReport = new ParseObject("ExpenseReport");
	private TouchImageView photoImage = null;
	private static int reportID = 0;
	private boolean isComplete = false;
	private EditText price;
	private EditText merchant;
	private EditText description;
	private EditText date;
	private EditText comment;
	private Spinner currency;
	private Spinner category;
	private Spinner payment;
	private Uri fileUri = null;
	private LinearLayout img;
	private Button submit;
	private SharedPreferences settings;
	private String imagePath;
	private Bitmap uploadable;

	public static final String PREFS_NAME = "MyPrefsFile";
	private static final String PROCESSING_FRAGMENT_TAG = "BACKEND_FRAGMENT";
	private static final String TAG = "CallCamera";
	private static final int CAPTURE_IMAGE_ACTIVITY_REQ = 0;
	private static final int SELECT_IMAGE = 1;

	/** Called when the activity is first created. */
	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addexpense);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		this.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

		submit = (Button) findViewById(R.id.button_submit);
		photoImage = (TouchImageView) findViewById(R.id.imageView1);
		price = (EditText) findViewById(R.id.addExpensePrice);
		merchant = (EditText) findViewById(R.id.addExpenseMerchant);
		description = (EditText) findViewById(R.id.addExpenseDescription);
		date = (EditText) findViewById(R.id.addExpenseDate);
		comment = (EditText) findViewById(R.id.addExpenseComments);
		currency = (Spinner) findViewById(R.id.addExpenseCurrency);
		category = (Spinner) findViewById(R.id.addExpenseCategory);
		payment = (Spinner) findViewById(R.id.addExpensePayment);

		//grab CURRENT user's current expense report
		//relative security measures needed
		ParseObject temp_ExpenseReport = expenseReport.getParseObject("" + reportID);
		// Restore date if inCompleteField is valid.
		if(!temp_ExpenseReport.getBoolean("isComplete"))
		{
			restoreFields();
		}

		img = (LinearLayout) findViewById(R.id.AddExpensesImageBackground);

		settings = getSharedPreferences(PREFS_NAME, 0);

		currency.setSelection(settings.getInt("index", 7));
		img.setBackgroundColor(Color.GRAY);

		final Calendar c = Calendar.getInstance();
		int yy = c.get(Calendar.YEAR);
		int mm = c.get(Calendar.MONTH);
		int dd = c.get(Calendar.DAY_OF_MONTH);

		date.setText(new StringBuilder().append(mm + 1).append("/").append(dd)
				.append("/").append(yy));

		Button callCameraButton = (Button) findViewById(R.id.button_camera);
		callCameraButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				fileUri = Uri.fromFile(getOutputPhotoFile());
				i.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
				startActivityForResult(i, CAPTURE_IMAGE_ACTIVITY_REQ);
			}
		});

		Button callGalleryButton = (Button) findViewById(R.id.button_gallery);
		callGalleryButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(i, SELECT_IMAGE);
			}
		});

		Bundle data = getIntent().getExtras();
		if (data.getBoolean("correct")) {
			setTitle("Correct Expense");

		}

	}

	private File getOutputPhotoFile() {
		File directory = new File(
				Environment
						.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				getPackageName());
		if (!directory.exists()) {
			if (!directory.mkdirs()) {
				Log.e(TAG, "Failed to create storage directory.");
				return null;
			}
		}
		String timeStamp = new SimpleDateFormat("yyyMMdd_HHmmss", Locale.US)
				.format(new Date());
		return new File(directory.getPath() + File.separator + "IMG_"
				+ timeStamp + ".jpg");
	}

	private void showPhoto(String photoUri) {
		File imageFile = new File(photoUri);
		if (imageFile.exists()) {
			Bitmap bitmap = BitmapFactory.decodeFile(imageFile
					.getAbsolutePath());
			try {
				ExifInterface exif = new ExifInterface(photoUri);
				int orientation = exif.getAttributeInt(
						ExifInterface.TAG_ORIENTATION, 1);
				if (orientation == 6) {
					Matrix matrix = new Matrix();
					matrix.postRotate(90);
					bitmap = Bitmap
							.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
									bitmap.getHeight(), matrix, true);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BitmapDrawable drawable = new BitmapDrawable(this.getResources(),
					bitmap);
			if (bitmap.getHeight() > 4096 || bitmap.getWidth() > 4096) {
				int nh = (int) (bitmap.getHeight() * (2048.0 / bitmap
						.getWidth()));
				Bitmap scaled = Bitmap.createScaledBitmap(bitmap, 2048, nh,
						true);
				drawable = new BitmapDrawable(this.getResources(), scaled);
			} else {
				drawable = new BitmapDrawable(this.getResources(), bitmap);
			}

			photoImage.setScaleType(ImageView.ScaleType.MATRIX);
			photoImage.setImageDrawable(drawable);
			img.setBackgroundColor(Color.TRANSPARENT);
		}
	}

	private void onClick_SubmitButton() {
		submit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				saveData();
			}
		});
	}

	private void saveData() {
		expenseReport.put("reportID", reportID);
		expenseReport.put("photoImage", photoImage);
		// The photo is the optional problem.
		// parse will query if photoimage is null
		if (photoImage != null) {
			isComplete = true;
			reportID++;
		}

		expenseReport.put("isComplete", isComplete);
		expenseReport.put("price", price.getText());
		expenseReport.put("merchant", merchant.getText());
		expenseReport.put("description", description.getText());
		expenseReport.put("date", date.getText());
		expenseReport.put("comment", comment.getText());
		expenseReport.put("currency", currency.getSelectedItem().toString());
		expenseReport.put("category", category.getSelectedItem().toString());
		expenseReport.put("payment", payment.getSelectedItem().toString());
		expenseReport.saveInBackground();
	}

	private void restoreFields() {
		reportID = expenseReport.getInt("reportID");
		// MEALSPOTTING APPLICATION for photos
		// photoImage = expenseReport.get("photoImage");

		price.setText("" + expenseReport.getDouble("price"));
		merchant.setText("" + expenseReport.getString("merchant"));
		description.setText("" + expenseReport.getString("description"));
		date.setText("" + expenseReport.getString("date"));
		comment.setText("" + expenseReport.getString("comment"));

//		restoreSpinnerField(currency, entries_currency,
//				expenseReport.getString("currency"));
//		restoreSpinnerField(category, entries_category,
//				expenseReport.getString("category"));
//		restoreSpinnerField(payment, entries_payment,
//				expenseReport.getString("payment"));
	}

	private void restoreSpinnerField(Spinner spinner, ArrayAdapter arr,
			String data) {
		ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter();
		int position = myAdap.getPosition(data);
		spinner.setSelection(position);
	}

	@Override
	protected void onPause() {
		super.onPause();
		saveData();

	}

	@Override
	protected void onStop() {
		super.onStop();
		saveData();
	}

}
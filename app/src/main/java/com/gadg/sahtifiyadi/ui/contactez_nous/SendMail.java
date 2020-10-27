package com.gadg.sahtifiyadi.ui.contactez_nous;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.gadg.sahtifiyadi.R;

import java.util.Properties;

import static com.gadg.sahtifiyadi.utilities.tools.isNetworkAvailable;


public class SendMail extends AsyncTask<Void,Void,Void> {

   private Context mContex;
   private Activity mActivity;
   private Session mSession;
   private String email, subject, mMessage;
   private Boolean hadSened = false;
   private ProgressDialog progressDialog;
   
   public SendMail(Context context, String mail, String title, String message){
	   this.mActivity = (Activity) context;
	   this.email = mail;
	   this.subject = title;
	   this.mMessage = message;

	   
   }

    @Override
   protected void onPreExecute() {
      super.onPreExecute();
      progressDialog = ProgressDialog.show(mContex,"Envoyant le message","Attendez un peu s'il vous plait...",false,false);
   }
   
   @Override
   protected void onPostExecute(Void aVoid) {
      super.onPostExecute(aVoid);
      progressDialog.dismiss();
	  AlertDialog.Builder builder = new AlertDialog.Builder(mContex);
                ViewGroup viewGroup = mActivity.findViewById(android.R.id.content);
                View dialogView;
	  if(hadSened){
		  
		  
                if (isNetworkAvailable(mContex)>0) {
                    dialogView = LayoutInflater.from(mContex).inflate(R.layout.success_custom_view, viewGroup, false);
                }else {
                    dialogView = LayoutInflater.from(mContex).inflate(R.layout.failed_custom_view, viewGroup, false);
                }
                builder.setView(dialogView);
                AlertDialog succussalertDialog = builder.create();
                succussalertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                succussalertDialog.show();
	  }else{
		  dialogView = LayoutInflater.from(mContex).inflate(R.layout.failed_custom_view, viewGroup, false);
         
                builder.setView(dialogView);
                AlertDialog succussalertDialog = builder.create();
                succussalertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                succussalertDialog.show();
	  }
   }
   @Override
   protected Void doInBackground(Void... params) {
      Properties props = new Properties();
      props.put("mail.smtp.host", "smtp.gmail.com");
      props.put("mail.smtp.socketFactory.port", "465");
      props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
      props.put("mail.smtp.auth", "true");
      props.put("mail.smtp.port", "465");
      mSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
         protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication("sahtifiyedi@gmail.com", "group08");
         }
      });
      try {
         MimeMessage mm = new MimeMessage(mSession);
         mm.setFrom(new InternetAddress("sahtifiyedi@gmail.com"));
         mm.addRecipient(Message.RecipientType.TO, new InternetAddress("akram.bensalem26@gmail.com"));
         mm.setSubject(subject);
         mm.setText("Email : "+email + "\n : "+mMessage);
         Transport.send(mm);
		 hadSened = true;
      }
      catch (MessagingException e) {
         e.printStackTrace();
          Log.d("ayentest",e.getMessage());
		 hadSened = false;
      }
      return null;
   }
   

}
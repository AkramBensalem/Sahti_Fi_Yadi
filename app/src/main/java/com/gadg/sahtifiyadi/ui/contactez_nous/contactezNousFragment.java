package com.gadg.sahtifiyadi.ui.contactez_nous;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.gadg.sahtifiyadi.MainActivity;
import com.gadg.sahtifiyadi.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class contactezNousFragment extends Fragment {
	private EditText mEmailEdit, mTitleEdit, mMessageEdit;
	private Map<String,Object> user;
	private DatabaseReference firebaseDatabase= FirebaseDatabase.getInstance().getReference().child("Contacts");;


	public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_contactez_nous, container, false);

        ((MainActivity) getActivity()).updateStatusBarColor("#295171");
        ((MainActivity) getActivity()).updateActionBarColorTWO("#6399d5");

		mEmailEdit = root.findViewById(R.id.contactez_nous_email_edit);
		mTitleEdit = root.findViewById(R.id.contactez_nous_title_message_edit);
		mMessageEdit = root.findViewById(R.id.contactez_nous_message_edit);
		
		root.findViewById(R.id.contactez_nous_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

				sendMail();

            }
        });

		
        return root;
    }
	
	public void sendMail(){
		        String email = mEmailEdit.getText().toString();
				String title = mTitleEdit.getText().toString();
				String message = mMessageEdit.getText().toString();
		SendNotification(email,title,message);


	}


	public void SendNotification(String email ,String title, String message){


    	    user = new HashMap<String, Object>();
			user.put("Email", email);
			user.put("Message", message);
			user.put("Title", title);
			String Id = UUID.randomUUID().toString();
			firebaseDatabase.child(Id).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
				@Override
				public void onComplete(@NonNull Task task) {
					if (task.isSuccessful()) {
						AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
						ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);
						View dialogView;
						dialogView = LayoutInflater.from(getContext()).inflate(R.layout.success_custom_view, viewGroup, false);
						builder.setView(dialogView);
						AlertDialog succussalertDialog = builder.create();
						succussalertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
						succussalertDialog.show();
						Toast.makeText(getContext(), "le message est envoyé", Toast.LENGTH_LONG).show();

					} else {
						String error;
						error = task.getException().getMessage();
						AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
						ViewGroup viewGroup = getActivity().findViewById(android.R.id.content);
						View dialogView;
						AlertDialog succussalertDialog = builder.create();
						dialogView = LayoutInflater.from(getContext()).inflate(R.layout.failed_custom_view, viewGroup, false);
						succussalertDialog.show();
						Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
					}
				}
			});
		}



}
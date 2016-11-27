package br.com.reymond.lawrence.oqrola.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.com.reymond.lawrence.oqrola.R;
import br.com.reymond.lawrence.oqrola.model.Contact;

public class MainActivityAux extends AppCompatActivity {
    private ContactRecyclerViewAdapter recycleViewAdapter;
    private List<Contact> contactBook = new ArrayList<>();
    private FirebaseDatabase database;
    private DatabaseReference contactBookRootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_aux);

        Toolbar toolbar = (Toolbar) findViewById(R.id.contactToolbar);
        setSupportActionBar(toolbar);

        recycleViewAdapter = new ContactRecyclerViewAdapter(this, contactBook);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.contactRecyclerView);
        recyclerView.setAdapter(recycleViewAdapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getContactBook();
    }

    private void getContactBook() {
        database = FirebaseDatabase.getInstance();
        contactBookRootRef = database.getReference("contact-book");

        contactBookRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                contactBook.clear();
                for (DataSnapshot c : dataSnapshot.getChildren()) {
                    Contact conctact = c.getValue(Contact.class);
                    contactBook.add(conctact);
                }
                recycleViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(MainActivityAux.this, R.string.error_realtime_get_data, Toast.LENGTH_LONG).show();
            }
        });
    }


    /**
     * Team View Holder
     */
    public class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView contactNameLabel;
        public TextView contactEmailLabel;
        public ImageView contactImageView;
        public Contact contact;

        public ContactViewHolder(View itemView) {
            super(itemView);

            this.contactNameLabel = (TextView) itemView.findViewById(R.id.contactNameLabel);
            this.contactEmailLabel = (TextView) itemView.findViewById(R.id.contactEmailLabel);
            this.contactImageView = (ImageView) itemView.findViewById(R.id.contactImageView);
            itemView.setOnClickListener(this);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) itemView.getLayoutParams();
            params.setMargins(35, 0, 0, 0);
            itemView.setLayoutParams(params);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(MainActivityAux.this, R.string.error_login, Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Contact RecyclerView Adapter
     */
    public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactViewHolder> {
        private Context context;
        private List<Contact> contactBook;

        public ContactRecyclerViewAdapter(Context context, List<Contact> contactBook) {
            this.context = context;
            this.contactBook = contactBook;
        }

        @Override
        public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.contact_list_item, parent, false);
            return new ContactViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ContactViewHolder holder, int position) {
            Contact contact = contactBook.get(position);

            holder.contactNameLabel.setText(contact.getName());
            holder.contactEmailLabel.setText(contact.getEmail());

            byte[] imageByteArray = Base64.decode(contact.getImage(), Base64.DEFAULT);

            Glide.with(context)
                    .load(imageByteArray)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .centerCrop()
                    .placeholder(R.drawable.default_user_gray)
                    .into(new BitmapImageViewTarget(holder.contactImageView) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            holder.contactImageView.setImageDrawable(circularBitmapDrawable);
                        }
                    });

            holder.contact = contact;
        }

        @Override
        public int getItemCount() {
            return contactBook.size();
        }
    }
}

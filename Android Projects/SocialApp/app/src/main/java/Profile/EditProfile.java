package Profile;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.socialapp.R;
import com.google.android.material.tabs.TabLayout;

import java.security.cert.TrustAnchor;

public class EditProfile extends AppCompatActivity {
    TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);

    }

}

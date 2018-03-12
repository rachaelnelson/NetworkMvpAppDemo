package io.rachaelnelson.architecturedemo.home;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.rachaelnelson.architecturedemo.R;
import io.rachaelnelson.architecturedemo.model_layer.User;

public class HomeAdapter extends RecyclerView.Adapter {

    private UserClickListener listener;

    public interface UserClickListener {
        void onUserClick(User user);
    }

    public HomeAdapter(List<User> users, UserClickListener listener) {
        this.users = users;
        this.listener = listener;
    }

    private List<User> users = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.list_item_github_user, parent, false);
        return new VHItem(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VHItem item = (VHItem) holder;

        final User user = users.get(position);

        Picasso.with(item.avatar.getContext()).load(user.getAvatarUrl()).into(((VHItem) holder).avatar);
        item.login.setText(user.getLogin());

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void appendItems(List<User> users) {
        users.addAll(users);
        notifyDataSetChanged();
    }

    class VHItem extends RecyclerView.ViewHolder {
        ImageView avatar;
        TextView login;

        public VHItem(View itemView) {
            super(itemView);
            avatar = itemView.findViewById(R.id.avatar);
            login = itemView.findViewById(R.id.login);

            itemView.setOnClickListener(v -> listener.onUserClick(users.get(getAdapterPosition())));
        }
    }
}

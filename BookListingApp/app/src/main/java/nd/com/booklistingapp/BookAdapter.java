package nd.com.booklistingapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by dell on 22-02-2017.
 */

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {

    private List<Book> bookList;

    public BookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listitem, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bkName.setText(book.getmBookName());
        holder.bkAuth.setText(book.getMbookAuth());
        holder.serial_no.setText(""+(position+1));
    }

    @Override
    public int getItemCount() {
        return bookList == null ? 0 : bookList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView bkName;
        private TextView bkAuth;
        private TextView serial_no;
        public MyViewHolder(View itemView) {
            super(itemView);
            this.bkName = (TextView) itemView.findViewById(R.id.bkname);
            this.bkAuth = (TextView) itemView.findViewById(R.id.bkauth);
            this.serial_no = (TextView) itemView.findViewById(R.id.serial_no);
        }
    }
}

package kate0013.cst2335.torunse.data;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainActivityViewModel extends ViewModel {
    public MutableLiveData<String> editTextContents=new MutableLiveData<>();
    public MutableLiveData<Boolean> isSelected=new MutableLiveData<>();
}

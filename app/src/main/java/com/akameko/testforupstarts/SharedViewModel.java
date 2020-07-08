package com.akameko.testforupstarts;

import androidx.lifecycle.ViewModel;

import com.akameko.testforupstarts.repository.pojos.Jeans;

public class SharedViewModel extends ViewModel {

    private Jeans activeJeans;

    public void setActiveJeans(Jeans activeJeans) {
        this.activeJeans = activeJeans;
    }

    public Jeans getActiveJeans() {
        return activeJeans;
    }
}

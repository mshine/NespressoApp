package com.ford.android.podtracker.adduser;

import com.ford.android.podtracker.data.UsersServiceApi;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by mshine7 on 22/09/2016.
 */
public class AddUserPresenterTest {

    @Mock
    private UsersServiceApi mUserServicesApi;

    @Mock
    private AddUserContract.View mAddUserView;

    private AddUserPresenter mAddUsersPresenter;

    @Before
    public void setupAddUserPresenter() {
        MockitoAnnotations.initMocks(this);

        mAddUsersPresenter = new AddUserPresenter(mUserServicesApi, mAddUserView);
    }

//    @Test
//    public void testSaveUser_showsSuccessMessageUi() {
//        mAddUsersPresenter.saveUser("Bertrand");
//
//        verify(mUserServicesApi).saveUser(any(User.class));
//        verify(mAddUserView).showUsersList(name);
//    }
}

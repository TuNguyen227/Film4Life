package com.nmt.film4life.module




import com.nmt.film4life.repository.Repository
import com.nmt.film4life.viewmodel.DetailViewModel
import com.nmt.film4life.viewmodel.SignInViewModel
import com.nmt.film4life.viewmodel.SignUpViewModel
import com.nmt.film4life.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val firebaseRepositoryModule= module {
    single {
        Repository(get())
    }
}
val signInViewModelModule= module {
    viewModel {
        SignInViewModel(get())
    }
}

val homeViewModelModule= module {
    viewModel{
        HomeViewModel(get())
    }
}
val signUpViewModelModule= module {
    viewModel{
        SignUpViewModel(get())
    }
}

val detailViewModelModule= module {
    viewModel{
        DetailViewModel(get())
    }
}
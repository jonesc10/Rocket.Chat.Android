package chat.rocket.android.dagger

import android.app.Application
import android.content.BroadcastReceiver
import chat.rocket.android.app.RocketChatApplication
import chat.rocket.android.chatroom.service.MessageService
import chat.rocket.android.dagger.module.ActivityBuilder
import chat.rocket.android.dagger.module.AppModule
import chat.rocket.android.dagger.module.ReceiverBuilder
import chat.rocket.android.dagger.module.ServiceBuilder
import chat.rocket.android.push.FirebaseTokenService
import chat.rocket.android.weblinks.ui.WebLinksAdapter
import chat.rocket.android.webview.weblink.ui.WebViewActivity
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    AppModule::class, ActivityBuilder::class, ServiceBuilder::class, ReceiverBuilder::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: RocketChatApplication)

    fun inject(service: FirebaseTokenService)

    fun inject(broadcastReceiver: BroadcastReceiver)

    fun inject(webLinksAdapter: WebLinksAdapter)

    fun inject(webViewActivity: WebViewActivity)

    fun inject(service: MessageService)

    /*@Component.Builder
    abstract class Builder : AndroidInjector.Builder<RocketChatApplication>()*/
}

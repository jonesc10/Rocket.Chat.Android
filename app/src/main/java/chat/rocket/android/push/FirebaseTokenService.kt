package chat.rocket.android.push

import chat.rocket.android.R
import chat.rocket.android.infrastructure.LocalRepository
import chat.rocket.common.RocketChatException
import chat.rocket.core.RocketChatClient
import chat.rocket.core.internal.rest.registerPushToken
import com.google.android.gms.gcm.GoogleCloudMessaging
import com.google.android.gms.iid.InstanceID
import com.google.firebase.iid.FirebaseInstanceIdService
import dagger.android.AndroidInjection
import kotlinx.coroutines.experimental.launch
import timber.log.Timber
import javax.inject.Inject

class FirebaseTokenService : FirebaseInstanceIdService() {

    @Inject
    lateinit var client: RocketChatClient

    @Inject
    lateinit var localRepository: LocalRepository

    override fun onCreate() {
        super.onCreate()
        AndroidInjection.inject(this);
    }

    override fun onTokenRefresh() {
        //TODO: We need to use the Cordova Project gcm_sender_id since it's the one configured on RC
        // default push gateway. We should register this project's own project sender id into it.
        val gcmToken = InstanceID.getInstance(this)
                .getToken(getString(R.string.gcm_sender_id), GoogleCloudMessaging.INSTANCE_ID_SCOPE, null)

        gcmToken?.let {
            localRepository.save(LocalRepository.KEY_PUSH_TOKEN, gcmToken)
            launch {
                try {
                    client.registerPushToken(gcmToken)
                } catch (ex: RocketChatException) {
                    Timber.e(ex)
                }
            }
        }
    }
}
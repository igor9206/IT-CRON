package ru.it_cron.intern2.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import ru.it_cron.intern2.R

object Intents {

    fun emailIntent(context: Context, email: String) {
        Intent(Intent.ACTION_SENDTO, Uri.parse(Constants.MAIL_TO)).apply {
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))

            context
                .packageManager
                .getLaunchIntentForPackage(Constants.GMAIL_PACKAGE)
                ?.let { context.startActivity(Intent(this)) } ?: createGmailDialog(context)
        }
    }

    fun facebookIntent(context: Context) {
        context
            .packageManager
            .getLaunchIntentForPackage(Constants.FACEBOOK_PACKAGE)
            ?.let { intent ->
                intent.data = Uri.parse(Constants.FACEBOOK_PACKAGE_URI + Constants.FACEBOOK_URL)
                context.startActivity(intent)
            } ?: browserIntent(Constants.FACEBOOK_URL, context)
    }

    fun instagramIntent(context: Context) {
        context
            .packageManager
            .getLaunchIntentForPackage(Constants.INSTAGRAM_PACKAGE)
            ?.let { intent ->
                intent.data = Uri.parse(Constants.INSTAGRAM_URL)
                context.startActivity(intent)
            } ?: browserIntent(Constants.INSTAGRAM_URL, context)
    }

    fun telegramIntent(context: Context) {
        context
            .packageManager
            .getLaunchIntentForPackage(Constants.TELEGRAM_PACKAGE)
            ?.let { intent ->
                intent.data = Uri.parse(Constants.TELEGRAM_URL)
                context.startActivity(intent)
            } ?: browserIntent(Constants.TELEGRAM_URL, context)
    }

    fun browserIntent(url: String, context: Context) {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
        )
    }

    fun shareIntent(content: String, context: Context) {
        val intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, content)
            type = "text/plain"
        }

        context.startActivity(
            Intent.createChooser(
                intent, ""
            )
        )
    }

    private fun createGmailDialog(context: Context) {
        MaterialAlertDialogBuilder(context)
            .setTitle(context.getString(R.string.recover_gmail))
            .setMessage(context.getString(R.string.gmail_google_play))
            .setNegativeButton(context.getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .setPositiveButton(context.getString(R.string.show_gmail_in_google_play)) { dialog, _ ->
                context.startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(Constants.MARKET_GMAIL)
                    )
                )
                dialog.dismiss()
            }
            .show()
    }
}
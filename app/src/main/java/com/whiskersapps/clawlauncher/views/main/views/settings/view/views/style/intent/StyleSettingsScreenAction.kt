package com.whiskersapps.clawlauncher.views.main.views.settings.view.views.style.intent

sealed class StyleSettingsScreenAction {
    data object NavigateBack : StyleSettingsScreenAction()
    data class SetIconPack(val packageName: String) : StyleSettingsScreenAction()
    data object OpenIconPackDialog: StyleSettingsScreenAction()
    data object CloseIconPackDialog: StyleSettingsScreenAction()
}
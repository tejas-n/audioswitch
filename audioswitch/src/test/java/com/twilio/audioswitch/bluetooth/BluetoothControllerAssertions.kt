package com.twilio.audioswitch.bluetooth

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothProfile
import android.content.Context
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.isA
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyZeroInteractions
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat

internal class BluetoothControllerAssertions {

    fun assertStart(
        context: Context,
        bluetoothHeadsetManager: BluetoothHeadsetManager,
        bluetoothDeviceReceiver: BluetoothHeadsetReceiver,
        headsetListener: BluetoothHeadsetConnectionListener,
        bluetoothAdapter: BluetoothAdapter
    ) {

        assertThat(bluetoothHeadsetManager.headsetListener, equalTo(headsetListener))
        assertThat(bluetoothDeviceReceiver.headsetListener, equalTo(headsetListener))
        verify(bluetoothAdapter).getProfileProxy(
                context,
                bluetoothHeadsetManager,
                BluetoothProfile.HEADSET)
        verify(context, times(3)).registerReceiver(
                eq(bluetoothDeviceReceiver), isA())
    }

    fun assertNotStarted(
        context: Context,
        bluetoothHeadsetManager: BluetoothHeadsetManager,
        bluetoothDeviceReceiver: BluetoothHeadsetReceiver,
        bluetoothAdapter: BluetoothAdapter
    ) {

        assertThat(bluetoothHeadsetManager.headsetListener, `is`(nullValue()))
        assertThat(bluetoothDeviceReceiver.headsetListener, `is`(nullValue()))
        verifyZeroInteractions(bluetoothAdapter)
        verify(context, times(0)).registerReceiver(
                eq(bluetoothDeviceReceiver), isA())
    }
}

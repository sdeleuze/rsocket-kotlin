package io.rsocket.android.internal

import io.rsocket.android.DuplexConnection
import io.rsocket.android.Frame
import io.rsocket.android.plugins.PluginRegistry
import org.junit.Assert
import org.junit.Test

class ClientDemuxerTest : ConnectionDemuxerTest() {

    override fun createDemuxer(conn: DuplexConnection,
                               pluginRegistry: PluginRegistry): ConnectionDemuxer =
            ClientConnectionDemuxer(conn, pluginRegistry)

    @Test
    override fun requester() {
        val frame = Frame.Error.from(1, RuntimeException())
        source.addToReceivedBuffer(frame)

        Assert.assertEquals(1, requesterFrames.get())
        Assert.assertEquals(0, responderFrames.get())
        Assert.assertEquals(0, setupFrames.get())
        Assert.assertEquals(0, serviceFrames.get())
    }

    @Test
    override fun responder() {
        val frame = Frame.Error.from(2, RuntimeException())
        source.addToReceivedBuffer(frame)

        Assert.assertEquals(0, requesterFrames.get())
        Assert.assertEquals(1, responderFrames.get())
        Assert.assertEquals(0, setupFrames.get())
        Assert.assertEquals(0, serviceFrames.get())
    }
}
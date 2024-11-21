import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.zaryabshakir.mediagallery.data.models.Bucket
import com.zaryabshakir.mediagallery.data.models.Media
import com.zaryabshakir.mediagallery.data.repository.MediaRepositoryImpl
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(MediaStore.Images.Media::class, ContentUris::class, Uri::class)
class MediaRepositoryImplTest {

    private lateinit var mediaRepository: MediaRepositoryImpl
    private lateinit var context: Context
    private lateinit var contentResolver: ContentResolver

    @Mock
    private lateinit var cursor: Cursor

    @Before
    fun setUp() {
        context = mock(Context::class.java)
        contentResolver = mock(ContentResolver::class.java)
        `when`(context.contentResolver).thenReturn(contentResolver)
        mediaRepository = MediaRepositoryImpl(context)
        PowerMockito.mockStatic(Uri::class.java)
        PowerMockito.mockStatic(ContentUris::class.java)

        val mockedUri = mock(Uri::class.java)
        `when`(Uri.parse("content://media/external/images/media/101L")).thenReturn(mockedUri)

        `when`(
            ContentUris.withAppendedId(
                eq(MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                eq(101L)
            )
        ).thenReturn(mockedUri)
    }


    // All Images
    @Test
    fun `getAllImageBuckets emits empty list when cursor is null`() = runTest {
        `when`(
            contentResolver.query(
                eq(MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                any(),
                isNull(),
                isNull(),
                any()
            )
        ).thenReturn(null)

        val result = mediaRepository.getAllImageBuckets().toList()

        assertEquals(1, result.size)
        assertEquals(emptyList<Bucket>(), result[0])
    }

    @Test
    fun `getAllImageBuckets emits empty list when cursor has no data`() = runTest {
        `when`(
            contentResolver.query(
                eq(MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                any(),
                isNull(),
                isNull(),
                any()
            )
        ).thenReturn(cursor)

        `when`(cursor.moveToFirst()).thenReturn(false)

        val result = mediaRepository.getAllImageBuckets().toList()

        assertEquals(1, result.size)
        assertEquals(emptyList<Bucket>(), result[0])
    }

    @Test
    fun `getAllImageBuckets emits list of buckets when cursor has data`() = runTest {
        `when`(
            contentResolver.query(
                eq(MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                any(),
                isNull(),
                isNull(),
                any()
            )
        ).thenReturn(cursor)

        `when`(cursor.moveToFirst()).thenReturn(true)
        `when`(cursor.moveToNext()).thenReturn(true, false)
        `when`(cursor.getColumnIndex(MediaStore.Images.Media._ID)).thenReturn(0)
        `when`(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID)).thenReturn(1)
        `when`(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)).thenReturn(2)
        `when`(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED)).thenReturn(3)

        `when`(cursor.getLong(0)).thenReturn(101L)
        `when`(cursor.getLong(1)).thenReturn(201L)
        `when`(cursor.getString(2)).thenReturn("BucketName")
        `when`(cursor.getLong(3)).thenReturn(1625097600L)
        val result = mediaRepository.getAllImageBuckets().toList()

        assertEquals(1, result.size)
        assertEquals(2, result[0].size)

        val bucket = result[0][0]
        assertEquals(-1, bucket.id)
        assertEquals("All", bucket.name)
    }

    @Test
    fun `getAllImageBuckets emits empty list on exception`() = runTest {
        `when`(
            contentResolver.query(
                eq(MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                any(),
                isNull(),
                isNull(),
                any()
            )
        ).thenThrow(RuntimeException("Test exception"))

        val result = mediaRepository.getAllImageBuckets().toList()

        assertEquals(1, result.size)
        assertEquals(emptyList<Bucket>(), result[0])
    }



    // All Videos
    @Test
    fun `getAllVideoBuckets emits empty list when cursor is null`() = runTest {
        `when`(
            contentResolver.query(
                eq(MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                any(),
                isNull(),
                isNull(),
                any()
            )
        ).thenReturn(null)

        val result = mediaRepository.getAllVideoBuckets().toList()

        assertEquals(1, result.size)
        assertEquals(emptyList<Bucket>(), result[0])
    }

    @Test
    fun `getAllVideoBuckets emits empty list when cursor has no data`() = runTest {
        `when`(
            contentResolver.query(
                eq(MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                any(),
                isNull(),
                isNull(),
                any()
            )
        ).thenReturn(cursor)

        `when`(cursor.moveToFirst()).thenReturn(false)

        val result = mediaRepository.getAllVideoBuckets().toList()

        assertEquals(1, result.size)
        assertEquals(emptyList<Bucket>(), result[0])
    }

    @Test
    fun `getAllVideoBuckets emits list of buckets when cursor has data`() = runTest {
        `when`(
            contentResolver.query(
                eq(MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                any(),
                isNull(),
                isNull(),
                any()
            )
        ).thenReturn(cursor)

        `when`(cursor.moveToFirst()).thenReturn(true)
        `when`(cursor.moveToNext()).thenReturn(true, false)
        `when`(cursor.getColumnIndex(MediaStore.Images.Media._ID)).thenReturn(0)
        `when`(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_ID)).thenReturn(1)
        `when`(cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)).thenReturn(2)
        `when`(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED)).thenReturn(3)

        `when`(cursor.getLong(0)).thenReturn(101L)
        `when`(cursor.getLong(1)).thenReturn(201L)
        `when`(cursor.getString(2)).thenReturn("BucketName")
        `when`(cursor.getLong(3)).thenReturn(1625097600L)
        val result = mediaRepository.getAllVideoBuckets().toList()

        assertEquals(1, result.size)
        assertEquals(2, result[0].size)

        val bucket = result[0][0]
        assertEquals(-1, bucket.id)
        assertEquals("All", bucket.name)
    }

    @Test
    fun `getAllVideoBuckets emits empty list on exception`() = runTest {
        `when`(
            contentResolver.query(
                eq(MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                any(),
                isNull(),
                isNull(),
                any()
            )
        ).thenThrow(RuntimeException("Test exception"))

        val result = mediaRepository.getAllVideoBuckets().toList()

        assertEquals(1, result.size)
        assertEquals(emptyList<Bucket>(), result[0])
    }

    //Get All Images from a Bucket
    @Test
    fun `getAllImages emits empty list when cursor is null`() = runTest {
        `when`(
            contentResolver.query(
                eq(MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                any(),
                isNull(),
                isNull(),
                any()
            )
        ).thenReturn(null)

        val result = mediaRepository.getAllImages("-1").toList()

        assertEquals(1, result.size)
        assertEquals(emptyList<Media>(), result[0])
    }

    @Test
    fun `getAllImages emits empty list when cursor has no data`() = runTest {
        `when`(
            contentResolver.query(
                eq(MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                any(),
                isNull(),
                isNull(),
                any()
            )
        ).thenReturn(cursor)

        `when`(cursor.moveToFirst()).thenReturn(false)

        val result = mediaRepository.getAllImages("-1").toList()

        assertEquals(1, result.size)
        assertEquals(emptyList<Media>(), result[0])
    }

    @Test
    fun `getAllImages emits list of media when cursor has data`() = runTest {
        `when`(
            contentResolver.query(
                eq(MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                any(),
                any(),
                any(),
                any()
            )
        ).thenReturn(cursor)

        `when`(cursor.moveToFirst()).thenReturn(true)
        `when`(cursor.moveToNext()).thenReturn(true, false) // Two rows
        `when`(cursor.getColumnIndex(MediaStore.Images.Media._ID)).thenReturn(0)
        `when`(cursor.getColumnIndex(MediaStore.Images.Media.TITLE)).thenReturn(1)
        `when`(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)).thenReturn(2)
        `when`(cursor.getColumnIndex(MediaStore.Images.Media.SIZE)).thenReturn(3)
        `when`(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE)).thenReturn(4)
        `when`(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED)).thenReturn(5)
        `when`(cursor.getLong(0)).thenReturn(101L) // _ID
        `when`(cursor.getString(1)).thenReturn("Title1") // TITLE
        `when`(cursor.getString(2)).thenReturn("DisplayName1") // DISPLAY_NAME
        `when`(cursor.getLong(3)).thenReturn(12345L) // SIZE
        `when`(cursor.getString(4)).thenReturn("image/jpeg") // MIME_TYPE
        `when`(cursor.getLong(5)).thenReturn(1625097600L) // DATE_MODIFIED

        val result = mediaRepository.getAllImages("123").toList()

        assertEquals(1, result.size)
        assertEquals(1, result[0].size)

        val media = result[0][0]
        assertEquals(101L, media.id)
        assertEquals("Title1", media.title)
        assertEquals("DisplayName1", media.displayName)
        assertEquals(12345L, media.size)
        assertEquals("image/jpeg", media.mimeType)
        assertEquals(1625097600L, media.dateModified)
    }

    @Test
    fun `getAllImages emits empty list on exception`() = runTest {
        `when`(
            contentResolver.query(
                eq(MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                any(),
                any(),
                any(),
                any()
            )
        ).thenThrow(RuntimeException("Test exception"))

        val result = mediaRepository.getAllImages("-1").toList()

        assertEquals(1, result.size)
        assertEquals(emptyList<Media>(), result[0])
    }



    //Get All Videos from a Bucket
    @Test
    fun `getAllVideos emits empty list when cursor is null`() = runTest {
        `when`(
            contentResolver.query(
                eq(MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                any(),
                isNull(),
                isNull(),
                any()
            )
        ).thenReturn(null)

        val result = mediaRepository.getAllVideos("-1").toList()

        assertEquals(1, result.size)
        assertEquals(emptyList<Media>(), result[0])
    }

    @Test
    fun `getAllVideos emits empty list when cursor has no data`() = runTest {
        `when`(
            contentResolver.query(
                eq(MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                any(),
                isNull(),
                isNull(),
                any()
            )
        ).thenReturn(cursor)

        `when`(cursor.moveToFirst()).thenReturn(false)

        val result = mediaRepository.getAllVideos("-1").toList()

        assertEquals(1, result.size)
        assertEquals(emptyList<Media>(), result[0])
    }

    @Test
    fun `getAllVideos emits list of media when cursor has data`() = runTest {
        `when`(
            contentResolver.query(
                eq(MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                any(),
                any(),
                any(),
                any()
            )
        ).thenReturn(cursor)

        `when`(cursor.moveToFirst()).thenReturn(true)
        `when`(cursor.moveToNext()).thenReturn(true, false) // Two rows
        `when`(cursor.getColumnIndex(MediaStore.Images.Media._ID)).thenReturn(0)
        `when`(cursor.getColumnIndex(MediaStore.Images.Media.TITLE)).thenReturn(1)
        `when`(cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME)).thenReturn(2)
        `when`(cursor.getColumnIndex(MediaStore.Images.Media.SIZE)).thenReturn(3)
        `when`(cursor.getColumnIndex(MediaStore.Images.Media.MIME_TYPE)).thenReturn(4)
        `when`(cursor.getColumnIndex(MediaStore.Images.Media.DATE_MODIFIED)).thenReturn(5)
        `when`(cursor.getLong(0)).thenReturn(101L) // _ID
        `when`(cursor.getString(1)).thenReturn("Title1") // TITLE
        `when`(cursor.getString(2)).thenReturn("DisplayName1") // DISPLAY_NAME
        `when`(cursor.getLong(3)).thenReturn(12345L) // SIZE
        `when`(cursor.getString(4)).thenReturn("image/jpeg") // MIME_TYPE
        `when`(cursor.getLong(5)).thenReturn(1625097600L) // DATE_MODIFIED

        val result = mediaRepository.getAllVideos("123").toList()

        assertEquals(1, result.size)

        val media = result[0][0]
        assertEquals(101L, media.id)
        assertEquals("Title1", media.title)
        assertEquals("DisplayName1", media.displayName)
        assertEquals(12345L, media.size)
        assertEquals("image/jpeg", media.mimeType)
        assertEquals(1625097600L, media.dateModified)
    }

    @Test
    fun `getAllVideos emits empty list on exception`() = runTest {
        `when`(
            contentResolver.query(
                eq(MediaStore.Images.Media.EXTERNAL_CONTENT_URI),
                any(),
                any(),
                any(),
                any()
            )
        ).thenThrow(RuntimeException("Test exception"))

        val result = mediaRepository.getAllVideos("-1").toList()

        assertEquals(1, result.size)
        assertEquals(emptyList<Media>(), result[0])
    }
}


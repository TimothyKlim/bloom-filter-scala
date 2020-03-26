package bloomfilter

import bloomfilter.util.Unsafe.unsafe

trait CanGetDataFrom[-From] {
  def getLong(from: From, offset: Int): Long
  def getByte(from: From, offset: Int): Byte
}

object CanGetDataFrom {

  implicit case object CanGetDataFromByteArray
      extends CanGetDataFrom[Array[Byte]] {

    override def getLong(buf: Array[Byte], offset: Int): Long = {
      (buf(offset + 7).toLong << 56) |
        ((buf(offset + 6) & 0xFFL) << 48) |
        ((buf(offset + 5) & 0xFFL) << 40) |
        ((buf(offset + 4) & 0xFFL) << 32) |
        ((buf(offset + 3) & 0xFFL) << 24) |
        ((buf(offset + 2) & 0xFFL) << 16) |
        ((buf(offset + 1) & 0xFFL) << 8) |
        buf(offset) & 0xFFL
    }

    override def getByte(from: Array[Byte], offset: Int): Byte = {
      from(offset)
    }
  }

  implicit case object CanGetDataFromArrayChar
      extends CanGetDataFrom[Array[Char]] {
    private val arrayCharOffset = unsafe.arrayBaseOffset(classOf[Array[Char]])

    override def getLong(from: Array[Char], offset: Int): Long = {
      unsafe.getLong(from, arrayCharOffset + offset.toLong)
    }

    override def getByte(from: Array[Char], offset: Int): Byte = {
      unsafe.getByte(from, arrayCharOffset + offset.toLong)
    }
  }
}

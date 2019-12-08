package com.example.endpoint.utils

import java.awt.Color
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.BufferedInputStream
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import javax.imageio.ImageIO

var width = 0
var height = 0

var red = 0
var green = 0
var blue = 0
var color : Color = Color.BLACK

fun grayscaleImage(byteArrayIn: ByteArray, file: File) {
        val image = ImageIO.read(ByteArrayInputStream(byteArrayIn))
        width = image.getWidth()
        height = image.getHeight()
        for (i in 0..height-1) {
            for (j in 0..width-1) {
                color  = Color(image.getRGB(j, i))
                red   = (color.getRed()   * 0.299).toInt()
                green = (color.getGreen() * 0.587).toInt()
                blue  = (color.getBlue()  * 0.114).toInt()
                image.setRGB(j, i, Color(red + green + blue, red + green + blue, red + green + blue ).getRGB())
            }
        }
        ImageIO.write(image, "jpg", file)
}
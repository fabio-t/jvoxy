package com.github.fabioticconi.libgdxtest;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

import static com.badlogic.gdx.graphics.VertexAttributes.Usage.*;

/**
 * Author: Fabio Ticconi
 * Date: 11/03/18
 */
public enum Block
{
    GRASS("grass", 0, 0),
    DIRT_GRASS("dirt-grass", 0, 0, 2, 0, 3, 0);

    public static int TEXTURE_WIDTH = 16;

    String name;

    int    topX, topY;
    int    bottomX, bottomY;
    int    sideX, sideY;

    TextureRegion topTexture = null;
    TextureRegion bottomTexture = null;
    TextureRegion sideTexture = null;

    Model model;

    Block(final String name, final int x, final int y)
    {
        this.name = name;

        this.topX = x;
        this.topY = y;
        this.bottomX = x;
        this.bottomY = y;
        this.sideX = x;
        this.sideY = y;
    }

    Block(final String name, final int topX, final int topY, final int bottomX, final int bottomY, final int sideX, final int sideY)
    {
        this.name = name;
        this.topX = topX;
        this.topY = topY;
        this.bottomX = bottomX;
        this.bottomY = bottomY;
        this.sideX = sideX;
        this.sideY = sideY;
    }

    public static void init(final float cubeSize)
    {
        final Texture      allTextures  = new Texture("textures.png");
        final ModelBuilder modelBuilder = new ModelBuilder();

        for (final Block type : values())
        {
            type.topTexture = new TextureRegion(allTextures,
                                                   type.topX * TEXTURE_WIDTH,
                                                   type.topY * TEXTURE_WIDTH,
                                                TEXTURE_WIDTH,
                                                TEXTURE_WIDTH);

            type.bottomTexture = new TextureRegion(allTextures,
                                                type.bottomX * TEXTURE_WIDTH,
                                                type.bottomY * TEXTURE_WIDTH,
                                                TEXTURE_WIDTH,
                                                TEXTURE_WIDTH);

            type.sideTexture = new TextureRegion(allTextures,
                                                type.sideX * TEXTURE_WIDTH,
                                                type.sideY * TEXTURE_WIDTH,
                                                TEXTURE_WIDTH,
                                                TEXTURE_WIDTH);

            final int attr = Position | Normal | TextureCoordinates;

            final Material material = new Material(TextureAttribute.createDiffuse(allTextures));

            modelBuilder.begin();
            final MeshPartBuilder mpb = modelBuilder.part("box", GL20.GL_TRIANGLES, attr, material);

            // front
            mpb.setUVRange(type.sideTexture);
            mpb.rect(cubeSize, 0, 0, 0f, 0f, 0f, 0f, cubeSize, 0, cubeSize, cubeSize, 0, 0, 0, -1);

            // back
            mpb.setUVRange(type.sideTexture);
            mpb.rect(0, 0, cubeSize, cubeSize, 0, cubeSize, cubeSize, cubeSize, cubeSize, 0f, cubeSize, cubeSize, 0, 0, 1);

            // bottom
            mpb.setUVRange(type.bottomTexture);
            mpb.rect(0, 0, cubeSize, 0f, 0f, 0f, cubeSize, 0f, 0f, cubeSize, 0f, cubeSize, 0, -1, 0);

            // top
            mpb.setUVRange(type.topTexture);
            mpb.rect(0, cubeSize, 0, 0, cubeSize, cubeSize, cubeSize, cubeSize, cubeSize, cubeSize, cubeSize, 0, 0, 1, 0);

            // left
            mpb.setUVRange(type.sideTexture);
            mpb.rect(0, 0, 0, 0, 0, cubeSize, 0, cubeSize, cubeSize, 0, cubeSize, 0, -1, 0, 0);

            // right
            mpb.setUVRange(type.sideTexture);
            mpb.rect(cubeSize, 0, cubeSize, cubeSize, 0, 0, cubeSize, cubeSize, 0, cubeSize, cubeSize, cubeSize, 1, 0, 0);

            type.model = modelBuilder.end();
        }
    }

    public ModelInstance make(final float x, final float y, final float z)
    {
        final ModelInstance instance = new ModelInstance(model);
        instance.transform.setToTranslation(x, y, z);

        return instance;
    }
}

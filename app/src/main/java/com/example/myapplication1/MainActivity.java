package com.example.myapplication1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;

import com.google.ar.core.Anchor;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.Color;
import com.google.ar.sceneform.rendering.MaterialFactory;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ShapeFactory;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class MainActivity extends AppCompatActivity {

    private ArFragment arFragment;
    private ModelRenderable sphereRenderable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arFragment = (ArFragment)getSupportFragmentManager().findFragmentById(R.id.ux_fragment);

        MaterialFactory.makeOpaqueWithColor(this,new Color(android.graphics.Color.BLUE))
                .thenAccept(material -> {
                    sphereRenderable = ShapeFactory.makeSphere(0.1f,new Vector3(0.0f,0.15f,0.0f), material);
                });
        arFragment.setOnTapArPlaneListener((HitResult hitResult, Plane plane, MotionEvent motionEvent)->{
                if(sphereRenderable==null)
                {
                    return;
                }
                if(plane.getType() != Plane.Type.HORIZONTAL_UPWARD_FACING)
                {
                    return;
                }

                // Create an Anchor

                Anchor anchor = hitResult.createAnchor();
                AnchorNode anchorNode = new AnchorNode(anchor);
                anchorNode.setParent(arFragment.getArSceneView().getScene());

                //Create a Sphere TransformableNode
                TransformableNode sphere = new TransformableNode(arFragment.getTransformationSystem());
                sphere.setParent(anchorNode);
                sphere.setRenderable(sphereRenderable);
                sphere.select();
            }
        );
    }
}

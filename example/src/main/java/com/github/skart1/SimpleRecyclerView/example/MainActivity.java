/*
 * Copyright 2014 Art Koff
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.skart1.SimpleRecyclerView.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.github.skart1.SimpleRecyclerView.example.Adapters.ExampleAdapter;
import com.github.skart1.SimpleRecyclerViewDivider.FlexibleDividerDecorator;
import com.github.skart1.SimpleRecyclerViewDivider.cache.SimpleDrawableCache;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        ExampleAdapter exampleAdapter = new ExampleAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(exampleAdapter);

        recyclerView.addItemDecoration(new FlexibleDividerDecorator(exampleAdapter, new SimpleDrawableCache(exampleAdapter)));
    }
}

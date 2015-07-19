/*
  Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.

  The MySQL Connector/J is licensed under the terms of the GPLv2
  <http://www.gnu.org/licenses/old-licenses/gpl-2.0.html>, like most MySQL Connectors.
  There are special exceptions to the terms and conditions of the GPLv2 as it is applied to
  this software, see the FLOSS License Exception
  <http://www.mysql.com/about/legal/licensing/foss-exception.html>.

  This program is free software; you can redistribute it and/or modify it under the terms
  of the GNU General Public License as published by the Free Software Foundation; version 2
  of the License.

  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
  without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
  See the GNU General Public License for more details.

  You should have received a copy of the GNU General Public License along with this
  program; if not, write to the Free Software Foundation, Inc., 51 Franklin St, Fifth
  Floor, Boston, MA 02110-1301  USA

 */

package testsuite.mysqlx.devapi;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class BindTest extends CollectionTest {
    @Before
    @Override
    public void setupCollectionTest() {
        super.setupCollectionTest();
    }

    @After
    @Override
    public void teardownCollectionTest() {
        super.teardownCollectionTest();
    }

    @Test
    @Ignore("Rafal's protocol changes for this not yet implemented in xplugin")
    public void removeWithBind() {
        this.collection.add("{\"x\":1}").execute();
        this.collection.add("{\"x\":2}").execute();
        this.collection.add("{\"x\":3}").execute();

        assertEquals(3, this.collection.count());

        assertTrue(this.collection.find("@.x = 3").execute().hasNext());
        this.collection.remove("@.x = ?").bind(new Object[] {3}).execute();
        assertEquals(2, this.collection.count());
        assertFalse(this.collection.find("@.x = 3").execute().hasNext());
    }

    @Test
    @Ignore("Rafal's protocol changes for this not yet implemented in xplugin")
    public void removeWithNamedBinds() {
        this.collection.add("{\"x\":1}").execute();
        this.collection.add("{\"x\":2}").execute();
        this.collection.add("{\"x\":3}").execute();

        assertEquals(3, this.collection.count());

        assertTrue(this.collection.find("@.x = 3").execute().hasNext());
        Map<String, Object> params = new HashMap<>();
        params.put("thePlaceholder", 3);
        this.collection.remove("@.x = :thePlaceholder").bind(params).execute();
        assertEquals(2, this.collection.count());
        assertFalse(this.collection.find("@.x = 3").execute().hasNext());
    }

    // TODO: more tests with unnamed (x = ?) and different bind value types
    // TODO: more tests find & modify
}

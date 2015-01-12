/*
 * -------------------------------------------------------------------
 *
 *   Copyright (c) 2013 Dave Parfitt
 *
 *   This file is provided to you under the Apache License,
 *   Version 2.0 (the "License"); you may not use this file
 *   except in compliance with the License.  You may obtain
 *   a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing,
 *   software distributed under the License is distributed on an
 *   "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *   KIND, either express or implied.  See the License for the
 *   specific language governing permissions and limitations
 *   under the License.
 * -------------------------------------------------------------------
 */

package com.metadave.etp.rep;
import com.ericsson.otp.erlang.OtpErlangObject;

import java.util.ArrayList;
import java.util.List;

public class ETPBinary extends ETPTerm{

    protected List<ETPBinary.ETPBinaryValue> value;


    public ETPBinary(List<ETPBinaryValue> value) {
        super(value);
    }

    public ETPBinary(ETPBinaryValue ... values) {
        List<ETPBinaryValue> vs = new ArrayList<ETPBinaryValue>();
        for(ETPBinaryValue v : values) {
            vs.add(v);
        }
        this.value = vs;
    }

    public ETPTerm getValue(int index) {
        // TODO: check for valid index ranges
        return value.get(index);
    }

    @Override
    public String toString() {
        StringBuilder b = new StringBuilder();
        b.append("<<");
        boolean isFirst = true;
        for(ETPBinaryValue v : value) {
            if(isFirst) {
                b.append(v.toString());
                isFirst = false;
            } else {
                b.append(",");
                b.append(v.toString());
            }
        }
        b.append(">>");
        return b.toString();
    }

    @Override
    public OtpErlangObject getOTP() {
        return null;
    }

    public static abstract class ETPBinaryValue extends ETPTerm {
        private Integer size = null;

        protected ETPBinaryValue(Object value) {
            this.value = value;
        }

        protected ETPBinaryValue(Object value, int size) {
            this.size = size;
            this.value = value;
        }

        protected ETPBinaryValue() {
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

    }

    public static class BinInt extends ETPBinaryValue{

        protected Integer value;


        @Override
        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

        public BinInt(Long value) {
            super(value);
        }

        public BinInt(Long value, int size) {
            super(value, size);
        }

        @Override
        public String toString() {
            if(this.getSize() != null) {
                return value.toString() + ":" + this.getSize().toString();
            } else {
                return value.toString();
            }
        }

        @Override
        public OtpErlangObject getOTP() {
            return null;
        }
    }

    public static class BinString extends ETPBinaryValue {

        protected String value;

        @Override
        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public BinString(String value) {
            super(value);
        }

        public BinString(String value, int size) {
            super(value, size);
        }

        @Override
        public String toString() {
            return "\"" + value + "\"";
        }

        @Override
        public OtpErlangObject getOTP() {
            return null;
        }
    }
}

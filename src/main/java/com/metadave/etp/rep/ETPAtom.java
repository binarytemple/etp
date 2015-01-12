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


import com.ericsson.otp.erlang.OtpErlangAtom;
import com.ericsson.otp.erlang.OtpErlangDecodeException;
import com.ericsson.otp.erlang.OtpErlangObject;
import com.ericsson.otp.erlang.OtpInputStream;

public class ETPAtom extends ETPTerm {
    public ETPAtom(String value) {
        super(value);
    }

    @Override
    public OtpErlangObject getOTP() throws OtpErlangDecodeException {
        if (value instanceof Boolean ) {
            return new OtpErlangAtom((Boolean) this.getValue());
        }else if(value instanceof String) {
            return new OtpErlangAtom((String) this.getValue());
        }else if(value instanceof OtpInputStream) {
            return new OtpErlangAtom((OtpInputStream) this.getValue());
        }


        throw new UnsupportedOperationException();


    }

}

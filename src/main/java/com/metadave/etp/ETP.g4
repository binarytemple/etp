grammar ETP;

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

@lexer::members {
    public static final int WHITESPACE = 1;
    public static final int COMMENTS = 2;
}

etp_terms:
    (etp_term DOT)+;

etp_term:
    etp_atom
    | etp_int
    | etp_float
    | etp_string
    | etp_bool
    | etp_list
    | etp_tuple
    | etp_binary
    | etp_pid
    | etp_fun
    | etp_ref
    ;

// TODO: $\n, 2#101
etp_int:    INT;
etp_float:  FLOAT;
etp_string: STRING;
etp_atom:   ID | IDSTRING;
etp_bool:   TRUE | FALSE;
etp_list:   LSQUARE (listitems+=etp_term (COMMA listitems+=etp_term)*)? RSQUARE;
etp_tuple:  LCURLY (tupleitems+=etp_term (COMMA tupleitems+=etp_term)*)? RCURLY;
etp_pid:    LESSTHAN PID GREATERTHAN;
etp_fun:    HASH FUN LESSTHAN (.)*? GREATERTHAN;
etp_binary: BINSTART (segments+=etp_binary_item (COMMA segments+=etp_binary_item)*)? BINEND;
etp_binary_item: val=INT (COLON size=INT)? | STRING;

etp_ref:    HASH REF LESSTHAN REFID GREATERTHAN;


FUN:           'Fun';
REF:           'Ref';
COMMA:         ',';
LSQUARE:       '[';
RSQUARE:       ']';

LCURLY:        '{';
RCURLY:        '}';
LESSTHAN:      '<';
GREATERTHAN:   '>';
COLON:         ':';
BINSTART:      '<<';
BINEND:        '>>';
TRUE:          'true';
FALSE:         'false';
AT:            '@';
HASH:          '#';
DOT:           '.';

ID          :       [a-z][A-Za-z_@.0-9]*;
IDSTRING  :  '\'' (IDESC|.)*? '\'';

fragment IDESC : '\\\'' | '\\\\' ;

INT         :   ('-')? DIGIT+;
FLOAT       :   ('-')? DIGIT+ DOT DIGIT*
               | ('-')?DOT DIGIT+
            ;

PID       :   DIGIT+ DOT DIGIT+ DOT DIGIT+;

REFID     :   DIGIT+ DOT DIGIT+ DOT DIGIT+ DOT DIGIT+;

fragment DIGIT  : '0' .. '9';



STRING  :  '"' (ESC|.)*? '"';
fragment ESC : '\\"' | '\\\\' ;

LINE_COMMENT  : '%' .*? '\r'? '\n' -> channel(COMMENTS) ;

WS      :       [ \t\r\n]+ -> channel(WHITESPACE);

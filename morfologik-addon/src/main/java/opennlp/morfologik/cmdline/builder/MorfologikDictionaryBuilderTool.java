/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package opennlp.morfologik.cmdline.builder;

import java.io.File;
import java.nio.charset.Charset;

import morfologik.stemming.Dictionary;
import opennlp.morfologik.builder.MorfologikDictionayBuilder;
import opennlp.tools.cmdline.BasicCmdLineTool;
import opennlp.tools.cmdline.CmdLineUtil;
import opennlp.tools.cmdline.TerminateToolException;

public class MorfologikDictionaryBuilderTool extends BasicCmdLineTool {

  interface Params extends MorfologikDictionaryBuilderParams {
  }

  public String getShortDescription() {
    return "builds a binary POS Dictionary using Morfologik";
  }

  public String getHelp() {
    return getBasicHelp(Params.class);
  }

  public void run(String[] args) {
    Params params = validateAndParseParams(args, Params.class);

    File dictInFile = params.getInputFile();
    File dictOutFile = params.getOutputFile();
    File propertiesFile = getExpectedPropertiesFile(dictOutFile);
    Charset encoding = params.getEncoding();

    CmdLineUtil.checkInputFile("dictionary input file", dictInFile);
    CmdLineUtil.checkOutputFile("dictionary output file", dictOutFile);
    CmdLineUtil.checkOutputFile("properties output file", propertiesFile);

    MorfologikDictionayBuilder builder = new MorfologikDictionayBuilder();
    try {
      builder.build(dictInFile, dictOutFile, propertiesFile, encoding,
          params.getFSADictSeparator(), params.getUsesPrefixes(),
          params.getUsesInfixes());
    } catch (Exception e) {
      throw new TerminateToolException(-1,
          "Error while creating Morfologik POS Dictionay: " + e.getMessage(), e);
    }

  }

  private File getExpectedPropertiesFile(File dictFile) {
    return new File(Dictionary.getExpectedFeaturesName(dictFile
        .getAbsolutePath()));
  }

}

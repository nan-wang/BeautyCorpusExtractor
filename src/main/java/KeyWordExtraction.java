/**
*  This file is part of FNLP (formerly FudanNLP).
*  
*  FNLP is free software: you can redistribute it and/or modify
*  it under the terms of the GNU Lesser General Public License as published by
*  the Free Software Foundation, either version 3 of the License, or
*  (at your option) any later version.
*  
*  FNLP is distributed in the hope that it will be useful,
*  but WITHOUT ANY WARRANTY; without even the implied warranty of
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*  GNU Lesser General Public License for more details.
*  
*  You should have received a copy of the GNU General Public License
*  along with FudanNLP.  If not, see <http://www.gnu.org/licenses/>.
*  
*  Copyright 2009-2014 www.fnlp.org. All rights reserved. 
*/

import org.fnlp.app.keyword.AbstractExtractor;
import org.fnlp.app.keyword.WordExtract;
import org.fnlp.nlp.cn.tag.CWSTagger;
import org.fnlp.nlp.corpus.StopWords;

/**
 * 关键词抽取使用示例
 * @author jyzhao,ltian
 *
 */
public class KeyWordExtraction {
	
	public static void main(String[] args) throws Exception {
		
		
		StopWords sw= new StopWords("models/stopwords");
		CWSTagger seg = new CWSTagger("models/seg.m");
		AbstractExtractor key = new WordExtract(seg,sw);

		String query = "资生堂新出的气垫BB，日系终于还是忍不住对气垫bb出手了，看评价不错忍不住买了一块。用下来感觉，果然最晚交卷的放的招最大资生堂这个气垫保留了传统气垫bb的优点，容易上妆，有一定的光泽度，又改良了气垫bb一直为人诟病的缺点。";
		System.out.println(key.extract(query, 5, true));
		
		//处理已经分好词的句子
		sw=null;
		key = new WordExtract(seg, sw);
		System.out.println(key.extract(query, 3));

		key = new WordExtract();
		System.out.println(key.extract(query, 3));

		
	}
}
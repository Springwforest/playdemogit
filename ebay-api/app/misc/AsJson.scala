package misc

import com.fasterxml.jackson.databind.ObjectMapper
import scala.beans.BeanProperty
import javax.script.ScriptEngineManager
import javax.script.ScriptContext

/**
 * 所有能够被序列化为JSON字符串的对象/类/特性 都必须继承<code>AsJson</code>
 *
 * @author mclaren
 */
trait R {
  case class RS(_code: Int, response_ : Any) {
    @BeanProperty
    var code = _code

    @BeanProperty
    var response = response_

    def asJson = {
      new ObjectMapper().writeValueAsString(this)
    }
  }

  def asR(code: Int) = RS(code, this)
}

trait AsJson extends R {
  def asJson = {
    new ObjectMapper().writeValueAsString(this)
  }

  implicit class AnyRefJsonLike(any: String) {
    def enhancement(script: String) = {
      val scriptEngine = new ScriptEngineManager().getEngineByExtension("js")
      val bindings = scriptEngine.createBindings()
      bindings.put("self", any)
      scriptEngine.setBindings(bindings, ScriptContext.ENGINE_SCOPE)
      scriptEngine.eval(script)
      scriptEngine.eval("(function(s){return JSON.stringify(s)})(self)").asInstanceOf[String]
    }
  }
}

object AsJson {
  def apply(ref: Any) = {
    new ObjectMapper().writeValueAsString(ref)
  }
}


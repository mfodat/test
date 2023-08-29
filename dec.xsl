<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:dp="http://www.datapower.com/extensions"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns:str="http://exslt.org/strings"
xmlns:regexp="http://exslt.org/regular-expressions"
extension-element-prefixes="dp str regexp" exclude-result-prefixes="dp str regexp"
version="1.0">

 <xsl:template match="/">
 
 
<a>
<x><xsl:value-of select="dp:decrypt-data('http://www.w3.org/2001/04/xmlenc#aes256-cbc','name:sharedSecretKey','AAIPcGFzc3dvcmQtY2xpZW50PaK4lLMTCkvAXL__3BzbGO3SNWGPIXaQKe_GhMsf2AkeOq6z5dOWFMnKN4wyahWuU188nvQ1rWczKt97gpVcJwVx2mAiWoJ_kaJJPLO1cn0')"/> </x>
<x><xsl:value-of select="dp:decrypt-data('http://www.w3.org/2009/xmlenc11#aes256-gcm','name:sharedSecretKey','AAIPcGFzc3dvcmQtY2xpZW50PaK4lLMTCkvAXL__3BzbGO3SNWGPIXaQKe_GhMsf2AkeOq6z5dOWFMnKN4wyahWuU188nvQ1rWczKt97gpVcJwVx2mAiWoJ_kaJJPLO1cn0')"/> </x>

<x><xsl:value-of select="dp:encrypt-data('http://www.w3.org/2009/xmlenc11#aes256-gcm','name:sharedSecretKey','hello')"/> </x>

</a>
  </xsl:template>
</xsl:stylesheet> 
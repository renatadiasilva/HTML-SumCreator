<?xml version="1.0" encoding="US-ASCII"?>
<html xsl:version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<body style="font-family:Calibri;font-size:13pt;background-color:#fff">
		
<!-- 		<div style="width: 1190px; display: block; margin-left: auto; margin-right: auto;"> -->
		<div style="display: block">
		
			<xsl:for-each select="noticias/noticia">
<!-- 				<div style="float: left; width:550px; max-width:550px; background-color:#ddd; padding:10px; margin:10px"> -->
				<div style="background-color:#BBFBD1; padding:10px; margin:10px; margin-left: auto; margin-right: auto;">
			
					
					
						<!-- Local -->
						<div style="font-size: 18pt; font-weight: bold; color:#078532">
							<p>Region: "<xsl:value-of select="@local" />"</p>
						</div>
					
					<a style="text-decoration: none; color:#333" target="_blank">
						<xsl:attribute name="href">
							<xsl:value-of select="url"/>
						</xsl:attribute>	
					
						<!-- Headline -->
						<div style="font-size: 24pt; font-weight: bold; text-decoration: underline; color:#555">
							<p><xsl:value-of select="titulo" /></p>
						</div>
					</a>
					
						<!-- Image -->
						<div style="width: 100%; margin: 0 auto;">
							<div style="display: table;margin: 0 auto;">
								<img>
									<xsl:attribute name="src">
										<xsl:value-of select="mediaurl" />
									</xsl:attribute>
								</img>
							</div>
						</div>
						
						<!-- Author - Time -->
						<div style="font-size: 14pt; color:#078532">
							<p><xsl:value-of select="autor" /><br/><br/><xsl:value-of select="data" /></p><br/>
						</div>
						
						<!-- Descricao -->
						<div style="font-size: 16pt; font-weight: bold; text-align:justify;color:#333">
							<xsl:for-each select="highlights">
								<p>&gt; <xsl:value-of select="." /><br/></p>
							</xsl:for-each>	
							<br/>						
						</div>
						
						<!-- Corpo -->
						<div style="font-size: 13pt;text-align:justify;color:#333">
							<p><xsl:value-of select="texto" /></p>	
							<br/>
						</div>
						
						<!-- Video -->
						<a style="text-decoration: none; color:#333" target="_blank">
							<xsl:attribute name="href">
								<xsl:value-of select="videourl"/>
							</xsl:attribute>
	
							<div style="font-size: 16pt; font-weight: bold; text-decoration: underline; color:#555">
								<p>See Video</p>
							</div>
						</a>
				
				</div>

			</xsl:for-each>
			
		</div>
		
	</body>
	
</html>
package vcfix;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = VCFix.MODID,
        version = VCFix.VERSION,
        name = VCFix.NAME,
        dependencies = "required-after:fermiumbooter@[1.3.0,);required-after:variedcommodities"
)
public class VCFix {
    public static final String MODID = "vcfix";
    public static final String VERSION = "1.0.1";
    public static final String NAME = "VCFix";
    public static final Logger LOGGER = LogManager.getLogger();
}
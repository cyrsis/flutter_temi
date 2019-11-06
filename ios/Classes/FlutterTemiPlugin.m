#import "FlutterTemiPlugin.h"
#import <flutter_temi/flutter_temi-Swift.h>

@implementation FlutterTemiPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftFlutterTemiPlugin registerWithRegistrar:registrar];
}
@end

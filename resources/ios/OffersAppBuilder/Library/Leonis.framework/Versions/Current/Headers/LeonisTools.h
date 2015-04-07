//
//  LeonisTools.h
//  Leonis
//
//  Created by Leonis&Co. on 2013/11/22.
//  Copyright (c) 2013年 Leonis&Co. All rights reserved.
//

#import <UIKit/UIKit.h>

UIColor* getColor( NSString* colorString );
UIColor* getColorWithColorTable( NSString* colorString, NSDictionary* colorTable );

UIColor* getColorFromSystemColorTable( NSString* colorString );
UIColor* getColorFromRGBColorCodeString( NSString* colorCodeString );
//
//  RecommendDetailsViewController.m
//  DemoOfferSDK
//
//  Created by TTA-Brown-005 on 2/5/15.
//  Copyright (c) 2015 TTA-Brown-005. All rights reserved.
//

#import "RecommendDetailsViewController.h"

@interface RecommendDetailsViewController ()

@end

@implementation RecommendDetailsViewController
@synthesize nameLabel, contentLabel ,imageView, offers;

- (void)viewDidLoad {
    [super viewDidLoad];
    if (offers != nil) {
        nameLabel.text = offers.name;
        
        NSString *htmlString = offers.content;
        NSAttributedString *attributedString = [[NSAttributedString alloc] initWithData:[htmlString dataUsingEncoding:NSUnicodeStringEncoding] options:@{ NSDocumentTypeDocumentAttribute: NSHTMLTextDocumentType } documentAttributes:nil error:nil];
        
        contentLabel.attributedText = attributedString;
        
        dispatch_async( dispatch_get_global_queue(DISPATCH_QUEUE_PRIORITY_BACKGROUND, 0 ), ^{
            [offers loadDetail];
            
            UIActivityIndicatorView* indicatorView = [[UIActivityIndicatorView alloc] initWithActivityIndicatorStyle:UIActivityIndicatorViewStyleGray];
            indicatorView.center = self.imageView.center;
            dispatch_async(dispatch_get_main_queue(), ^{
                [self.view addSubview:indicatorView];
                [indicatorView startAnimating];
            });
            [offers loadImagesWithCallbackBlock:^(BOOL result) {
                dispatch_async(dispatch_get_main_queue(), ^{
                    imageView.image = offers.image;
                    [indicatorView removeFromSuperview];
                });
            }];
        });
    }
    
    // Do any additional setup after loading the view from its nib.
}


- (void)didReceiveMemoryWarning {
    [super didReceiveMemoryWarning];
    // Dispose of any resources that can be recreated.
}

/*
#pragma mark - Navigation

// In a storyboard-based application, you will often want to do a little preparation before navigation
- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    // Get the new view controller using [segue destinationViewController].
    // Pass the selected object to the new view controller.
}
*/

@end
